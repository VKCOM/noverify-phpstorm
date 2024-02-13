package com.vk.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Tag
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl
import com.jetbrains.php.config.interpreters.PhpSdkDependentConfiguration
import com.jetbrains.php.tools.quality.QualityToolConfiguration

@Tag("noverify_by_interpreter")
class NoVerifyRemoteConfiguration : NoVerifyConfiguration(), PhpSdkDependentConfiguration {
    private var myInterpreterId: String? = null

    @Attribute("interpreter_id")
    override fun getInterpreterId(): String? {
        return myInterpreterId
    }

    override fun setInterpreterId(interpreterId: String) {
        myInterpreterId = interpreterId
    }

    override fun getPresentableName(project: Project?): String {
        return getDefaultName(PhpInterpretersManagerImpl.getInstance(project).findInterpreterName(interpreterId))
    }

    override fun getId(): String {
        val interpreterId = interpreterId
        return if (StringUtil.isEmpty(interpreterId)) "Undefined interpreter" else interpreterId!!
    }

    private fun getDefaultName(interpreterName: String?): String {
        return if (interpreterName?.isEmpty() == true) {
            "Undefined interpreter"
        } else {
            "Interpreter: $interpreterName"
        }
    }

    override fun clone(): QualityToolConfiguration {
        val settings = NoVerifyRemoteConfiguration().also {
            it.myInterpreterId = this.myInterpreterId
        }

        super.clone(settings)

        return settings
    }

    override fun serialize(path: String?): String? {
        return path
    }

    override fun deserialize(path: String?): String? {
        return path
    }
}
