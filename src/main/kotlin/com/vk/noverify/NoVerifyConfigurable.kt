package com.vk.noverify

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolProjectConfigurableForm
import com.jetbrains.php.tools.quality.QualityToolType

class NoVerifyConfigurable(project: Project) : QualityToolProjectConfigurableForm(project), Configurable.NoScroll {
    override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
        return NoVerifyQualityToolType.INSTANCE
    }
}
