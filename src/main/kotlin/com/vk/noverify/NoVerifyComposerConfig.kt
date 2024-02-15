package com.vk.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.jetbrains.php.composer.actions.log.ComposerLogMessageBuilder
import com.jetbrains.php.tools.quality.QualityToolConfigurationManager
import com.jetbrains.php.tools.quality.QualityToolsComposerConfig
import com.vk.noverify.NoVerifyOpenSettingsProvider.Companion.NOVERIFY_OPEN_SETTINGS_PROVIDER

class NoVerifyComposerConfig :
    QualityToolsComposerConfig<NoVerifyConfiguration, NoVerifyValidationInspection>(PACKAGE, RELATIVE_PATH) {
    override fun getQualityInspectionShortName(): String {
        return NoVerifyQualityToolType.INSTANCE.inspectionId
    }

    override fun getConfigurationManager(project: Project): QualityToolConfigurationManager<NoVerifyConfiguration> {
        return NoVerifyConfigurationManager.getInstance(project)
    }

    override fun getSettings(): ComposerLogMessageBuilder.Settings {
        return NOVERIFY_OPEN_SETTINGS_PROVIDER
    }

    companion object {
        private const val PACKAGE: String = "vkcom/noverify"
        private val RELATIVE_PATH: String = "bin/noverify${if (SystemInfo.isWindows) ".exe" else ""}"
    }
}
