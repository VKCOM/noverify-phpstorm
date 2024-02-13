package com.vk.noverify

import com.intellij.util.xmlb.XmlSerializer
import com.jetbrains.php.tools.quality.QualityToolConfigurationBaseManager
import com.jetbrains.php.tools.quality.QualityToolType
import org.jdom.Element

open class NoVerifyConfigurationBaseManager : QualityToolConfigurationBaseManager<NoVerifyConfiguration>() {
    override fun getQualityToolType(): QualityToolType<NoVerifyConfiguration> {
        return NoVerifyQualityToolType.INSTANCE
    }

    override fun getOldStyleToolPathName() = NOVERIFY_PATH

    override fun getConfigurationRootName() = NOVERIFY_ROOT_NAME

    override fun loadLocal(element: Element?): NoVerifyConfiguration? {
        if (element == null) {
            return null
        }

        return XmlSerializer.deserialize(element, NoVerifyConfiguration::class.java)
    }

    companion object {
        const val NOVERIFY: String = "NoVerify"
        const val NOVERIFY_PATH: String = "NoVerifyPath"
        const val NOVERIFY_ROOT_NAME: String = "NoVerify_settings"
    }
}
