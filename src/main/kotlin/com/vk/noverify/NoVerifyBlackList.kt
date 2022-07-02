package com.vk.noverify

import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.jetbrains.php.tools.quality.QualityToolBlackList

@State(name = "NoVerifyBlackList", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class NoVerifyBlackList : QualityToolBlackList() {
    companion object {
        fun getInstance(project: Project): NoVerifyBlackList {
            return project.getService(NoVerifyBlackList::class.java)
        }
    }
}
