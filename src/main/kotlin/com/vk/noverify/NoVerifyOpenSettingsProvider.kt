package com.vk.noverify

import com.intellij.openapi.project.Project
import com.jetbrains.php.composer.actions.log.ComposerLogMessageBuilder
import com.jetbrains.php.tools.quality.QualityToolConfigurableList
import com.jetbrains.php.tools.quality.QualityToolType
import com.jetbrains.php.ui.PhpUiUtil

class NoVerifyOpenSettingsProvider : ComposerLogMessageBuilder.Settings("\u200C") {
    override fun show(project: Project) {
        PhpUiUtil.editConfigurable(
            project,
            object :
                QualityToolConfigurableList<NoVerifyConfiguration>(project, NoVerifyQualityToolType.INSTANCE, null) {
                override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
                    return NoVerifyQualityToolType.INSTANCE
                }
            })
    }

    companion object {
        val NOVERIFY_OPEN_SETTINGS_PROVIDER = NoVerifyOpenSettingsProvider()
    }
}
