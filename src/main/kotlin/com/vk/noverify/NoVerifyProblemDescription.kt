package com.vk.noverify

import com.intellij.openapi.util.NlsSafe
import com.jetbrains.php.tools.quality.QualityToolMessage
import com.jetbrains.php.tools.quality.QualityToolXmlMessageProcessor

class NoVerifyProblemDescription(
    severity: QualityToolMessage.Severity?,
    lineNumber: Int,
    val startChar: Int,
    val endChar: Int,
    message: @NlsSafe String?,
    file: String?
) : QualityToolXmlMessageProcessor.ProblemDescription(
    severity,
    lineNumber,
    startChar,
    message,
    file,
)
