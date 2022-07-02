package com.vk.noverify

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.Transient
import com.jetbrains.php.tools.quality.QualityToolConfiguration

open class NoVerifyConfiguration : QualityToolConfiguration {
    private var myNoVerifyPath = ""
    private var myTimeoutMs = 30000
    private var myMaxMessagesPerFile = 100

    var asKphp = false
        @Attribute("as_kphp")
        get

    var coresCount = Runtime.getRuntime().availableProcessors()
        @Attribute("cores_count")
        get

    var excludeRegexp = ""
        @Attribute("exclude_regexp")
        get

    var unusedVarRegexp = ""
        @Attribute("unused_var_regexp")
        get

    var cachePath = ""
        @Attribute("cache_path")
        get

    var customParameters = ""
        @Attribute("custom_parameters")
        get

    override fun compareTo(other: QualityToolConfiguration?): Int {
        if (other !is NoVerifyConfiguration) {
            return 1
        }
        if (this.getPresentableName(null) == "Local") {
            return -1
        }
        if (other.getPresentableName(null) == "Local") {
            return 1
        }

        return StringUtil.compare(getPresentableName(null), other.getPresentableName(null), false)
    }

    override fun getId(): String {
        return "Local"
    }

    override fun getPresentableName(project: Project?): String {
        return id
    }

    override fun getInterpreterId(): String? {
        return null
    }

    @Attribute("timeout")
    override fun getTimeout(): Int {
        return myTimeoutMs
    }

    override fun setTimeout(timeout: Int) {
        myTimeoutMs = timeout
    }

    @Transient
    override fun getToolPath(): String {
        return myNoVerifyPath
    }

    override fun setToolPath(toolPath: String) {
        myNoVerifyPath = toolPath
    }

    @Attribute("tool_path")
    fun getSerializedToolPath(): String {
        return serialize(myNoVerifyPath).removeSuffix(".bat")
    }

    fun setSerializedToolPath(configurationFilePath: String?) {
        val deserializePath = deserialize(configurationFilePath).removeSuffix(".bat")

        myNoVerifyPath = deserializePath
    }

    @Attribute("max_messages_per_file")
    override fun getMaxMessagesPerFile(): Int {
        return myMaxMessagesPerFile
    }

    override fun clone(): QualityToolConfiguration {
        val settings = NoVerifyConfiguration()
        clone(settings)
        return settings
    }

    fun clone(settings: NoVerifyConfiguration): NoVerifyConfiguration {
        return settings.also {
            it.myNoVerifyPath = myNoVerifyPath
            it.myMaxMessagesPerFile = myMaxMessagesPerFile
            it.myTimeoutMs = myTimeoutMs
            it.asKphp = asKphp
            it.coresCount = coresCount
            it.excludeRegexp = excludeRegexp
            it.unusedVarRegexp = unusedVarRegexp
            it.cachePath = cachePath
            it.customParameters = customParameters
        }
    }
}
