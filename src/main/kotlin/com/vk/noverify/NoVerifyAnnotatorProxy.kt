package com.vk.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.config.PhpRuntimeConfiguration
import com.jetbrains.php.tools.quality.*

open class NoVerifyAnnotatorProxy : QualityToolAnnotator<NoVerifyValidationInspection>() {
    override fun getOptions(
        filePath: String?, inspection: NoVerifyValidationInspection, profile: InspectionProfile?, project: Project,
    ): List<String> {
        if (filePath == null) {
            return emptyList()
        }

        val tool = qualityToolType.getGlobalTool(project, profile) as? NoVerifyGlobalInspection ?: return emptyList()
        val projectPath = project.basePath ?: return emptyList()
        val config = getConfiguration(project, inspection) as? NoVerifyConfiguration ?: return emptyList()

        val phpConfig = PhpRuntimeConfiguration.getInstance(project)
        val stubsPath = phpConfig.defaultStubsPath

        return tool.getCommandLineOptions(
            projectPath,
            filePath,
            config.asKphp,
            stubsPath,
            config.coresCount,
            config.excludeRegexp,
            config.unusedVarRegexp,
            config.cachePath,
            config.customParameters,
        )
    }

    override fun getTemporaryFilesFolder(): String {
        return TEMP_FOLDER
    }

    override fun createAnnotatorInfo(
        file: PsiFile?,
        tool: NoVerifyValidationInspection,
        inspectionProfile: InspectionProfile,
        project: Project,
        configuration: QualityToolConfiguration,
        isOnTheFly: Boolean,
    ): QualityToolAnnotatorInfo<NoVerifyValidationInspection> {
        if (!isOnTheFly) {
            LOG.warn("isOnTheFly is False")
        }

        return NoVerifyQualityToolAnnotatorInfo(file, tool, inspectionProfile, project, configuration, isOnTheFly)
    }

    override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
        return NoVerifyQualityToolType.INSTANCE
    }

    override fun createMessageProcessor(collectedInfo: QualityToolAnnotatorInfo<*>): QualityToolMessageProcessor {
        return NoVerifyMessageProcessor(collectedInfo)
    }

    override fun getPairedBatchInspectionShortName(): String {
        return qualityToolType.inspectionId
    }

    companion object {
        val INSTANCE = NoVerifyAnnotatorProxy()

        private val LOG: Logger = Logger.getInstance(NoVerifyAnnotatorProxy::class.java)

        const val TEMP_FOLDER = "noverify_temp_folder"
    }
}
