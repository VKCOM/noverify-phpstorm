package com.vk.noverify

import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializer
import com.jetbrains.php.config.interpreters.PhpInterpreter
import com.jetbrains.php.remote.tools.quality.QualityToolByInterpreterConfigurableForm
import com.jetbrains.php.tools.quality.QualityToolConfigurableForm
import org.jdom.Element
import org.jetbrains.annotations.NonNls

class NoVerifyRemoteConfigurationProvider : NoVerifyConfigurationProvider() {
    override fun canLoad(tagName: String): Boolean {
        return tagName == NOVERIFY_BY_INTERPRETER
    }

    override fun load(element: Element): NoVerifyConfiguration {
        return XmlSerializer.deserialize(element, NoVerifyRemoteConfiguration::class.java)
    }

    override fun createConfigurationForm(
        project: Project,
        settings: NoVerifyConfiguration
    ): QualityToolConfigurableForm<*>? {
        if (settings !is NoVerifyRemoteConfiguration) {
            return null
        }

        val delegate = NoVerifyConfigurableForm(project, settings)
        return QualityToolByInterpreterConfigurableForm(
            project,
            settings,
            delegate,
        )
    }

    override fun createNewInstance(
        project: Project?,
        existingSettings: List<NoVerifyConfiguration>
    ): NoVerifyConfiguration? {
        return null
    }

    override fun createConfigurationByInterpreter(interpreter: PhpInterpreter): NoVerifyConfiguration {
        val settings = NoVerifyRemoteConfiguration()
        settings.setInterpreterId(interpreter.id)
        return settings
    }

    companion object {
        private const val NOVERIFY_BY_INTERPRETER: @NonNls String = "noverify_by_interpreter"
    }
}
