package com.vk.noverify

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.jetbrains.php.tools.quality.QualityToolProjectConfiguration
import com.jetbrains.php.tools.quality.QualityToolType

@State(name = "NoVerifyProjectConfiguration", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class NoVerifyProjectConfiguration : QualityToolProjectConfiguration<NoVerifyConfiguration>(),
    PersistentStateComponent<NoVerifyProjectConfiguration> {
    override fun getState(): NoVerifyProjectConfiguration {
        return this
    }

    override fun loadState(state: NoVerifyProjectConfiguration) {
        XmlSerializerUtil.copyBean(state, this)
    }

    override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
        return NoVerifyQualityToolType.INSTANCE
    }

    companion object {
        fun getInstance(project: Project): NoVerifyProjectConfiguration {
            return project.getService(NoVerifyProjectConfiguration::class.java)
        }
    }
}
