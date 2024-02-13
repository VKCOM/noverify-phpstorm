package com.vk.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Pair
import com.jetbrains.php.PhpBundle
import com.jetbrains.php.tools.quality.*
import com.vk.noverify.NoVerifyConfigurationBaseManager.Companion.NOVERIFY

class NoVerifyConfigurableForm(project: Project, configuration: NoVerifyConfiguration) :
    QualityToolConfigurableForm<NoVerifyConfiguration>(project, configuration, NOVERIFY, "Noverify") {
    override fun getQualityToolType(): QualityToolType<QualityToolConfiguration> {
        @Suppress("UNCHECKED_CAST")
        return NoVerifyQualityToolType.INSTANCE as QualityToolType<QualityToolConfiguration>
    }

    override fun getCustomConfigurable(
        project: Project, configuration: NoVerifyConfiguration,
    ): QualityToolCustomSettings {
        return NoVerifyCustomOptionsForm(project, configuration)
    }

    override fun getHelpTopic(): String {
        return "reference.settings.php.NoVerify"
    }

    override fun validateWithNoAnsi(): Boolean {
        return false
    }

    override fun getVersionOptions(): Array<String> {
        return arrayOf("version")
    }

    override fun validateMessage(message: String): Pair<Boolean, String> {
        val regex = Regex("^NoVerify, version (?<version>\\d.\\d.\\d)?")
        val version = regex.find(message)?.groups?.get("version")
        if (version == null) {
            Pair.create(false, PhpBundle.message("quality.tool.can.not.determine.version", message))
        }

        return Pair.create(true, "OK, NoVerify version $version")
    }
}
