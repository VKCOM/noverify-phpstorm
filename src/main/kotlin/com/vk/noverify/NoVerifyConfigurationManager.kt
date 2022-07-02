package com.vk.noverify

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolConfigurationManager

class NoVerifyConfigurationManager(project: Project?) :
    QualityToolConfigurationManager<NoVerifyConfiguration>(project) {
    init {
        if (project != null) {
            myProjectManager = project.getService(NoVerifyProjectConfigurationManager::class.java)
        }
        myApplicationManager =
            ApplicationManager.getApplication().getService(NoVerifyAppConfigurationManager::class.java)
    }

    @State(name = "NoVerify", storages = [Storage("php.xml")])
    internal class NoVerifyProjectConfigurationManager : NoVerifyConfigurationBaseManager()

    @State(name = "NoVerify", storages = [Storage("php.xml")])
    internal class NoVerifyAppConfigurationManager : NoVerifyConfigurationBaseManager()

    companion object {
        fun getInstance(project: Project): NoVerifyConfigurationManager {
            return project.getService(NoVerifyConfigurationManager::class.java)
        }
    }
}
