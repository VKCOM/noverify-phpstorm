package com.vk.noverify

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.jetbrains.php.tools.quality.*
import org.jetbrains.annotations.NonNls

class NoVerifyMessageProcessor(private val info: QualityToolAnnotatorInfo<*>) : QualityToolMessageProcessor(info) {
    override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
        return NoVerifyQualityToolType.INSTANCE
    }

    override fun getMessagePrefix(): @NonNls String {
        return "noverify"
    }

    override fun parseLine(line: String) {
        val outputLine = line.trim()
        if (!outputLine.startsWith(MESSAGE_START) || !outputLine.endsWith(MESSAGE_END)) {
            return
        }

        val messageHandler = NoVerifyJsonMessageHandler()
        messageHandler.parseJson(outputLine)

        val psiFile = info.psiFile ?: return
        val document = PsiDocumentManager.getInstance(psiFile.project).getDocument(psiFile) ?: return

        for (problem in messageHandler.problemList) {
            val startLine = document.getLineStartOffset(problem.lineNumber - 1)
            val textRange = TextRange.create(startLine + problem.startChar, startLine + problem.endChar)

            val message = QualityToolMessage(this, textRange, problem.severity, problem.message)
            addMessage(message)
        }
    }

    override fun severityToDisplayLevel(severity: QualityToolMessage.Severity): HighlightDisplayLevel? {
        return HighlightDisplayLevel.find(severity.name)
    }

    override fun addMessage(message: QualityToolMessage) {
        super.addMessage(message)
    }

    override fun done() {
    }

    private class NoVerifyJsonMessageHandler {
        val problemList: MutableList<NoVerifyProblemDescription> = mutableListOf()

        fun parseJson(line: String) {
            val parser = JsonParser.parseString(line)

            val reports = parser.asJsonObject.get("Reports").asJsonArray
            reports.forEach { report: JsonElement ->
                val jsonReport = report.asJsonObject

                val problem = parseReport(jsonReport)
                problemList.add(problem)
            }
        }

        fun parseReport(jsonReport: JsonObject): NoVerifyProblemDescription {
            return NoVerifyProblemDescription(
                levelToSeverity(jsonReport.get("level").asInt),
                jsonReport.get("line").asInt,
                jsonReport.get("start_char").asInt,
                jsonReport.get("end_char").asInt,
                jsonReport.get("message").asString,
                jsonReport.get("filename").asString,
            )
        }

        /**
         * @see <a href="https://github.com/VKCOM/noverify/blob/master/src/linter/report.go#L1153">report.go</a>
         * @see <a href="https://github.com/VKCOM/noverify/blob/master/src/linter/lintapi/lintapi.go">lintapi.go</a>
         */
        fun levelToSeverity(level: Int?): QualityToolMessage.Severity? {
            return when (level) {
                1 -> QualityToolMessage.Severity.ERROR
                2 -> QualityToolMessage.Severity.WARNING
                3 -> QualityToolMessage.Severity.WARNING
                4 -> QualityToolMessage.Severity.WARNING
                else -> null
            }
        }
    }

    companion object {
        private const val MESSAGE_START: @NonNls String = "{"
        private const val MESSAGE_END: @NonNls String = "}"
    }
}
