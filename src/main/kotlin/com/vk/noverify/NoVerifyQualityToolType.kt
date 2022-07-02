package com.vk.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.profile.codeInspection.InspectionProjectProfileManager
import com.intellij.util.ObjectUtils.tryCast
import com.jetbrains.php.tools.quality.*
import com.vk.noverify.NoVerifyConfigurationBaseManager.Companion.NOVERIFY

class NoVerifyQualityToolType : QualityToolType<NoVerifyConfiguration>() {
    override fun getDisplayName(): String {
        return NOVERIFY
    }

    override fun getQualityToolBlackList(project: Project): QualityToolBlackList {
        return NoVerifyBlackList.getInstance(project)
    }

    override fun getConfigurationManager(project: Project): QualityToolConfigurationManager<NoVerifyConfiguration> {
        return NoVerifyConfigurationManager.getInstance(project)
    }

    override fun getInspection(): QualityToolValidationInspection {
        return NoVerifyValidationInspection()
    }

    override fun getConfigurationProvider(): QualityToolConfigurationProvider<NoVerifyConfiguration>? {
        return NoVerifyConfigurationProvider.getInstances()
    }

    override fun createConfigurableForm(
        project: Project, settings: NoVerifyConfiguration
    ): QualityToolConfigurableForm<NoVerifyConfiguration> {
        return NoVerifyConfigurableForm(project, settings)
    }

    override fun getToolConfigurable(project: Project): Configurable {
        return NoVerifyConfigurable(project)
    }

    override fun getProjectConfiguration(project: Project): QualityToolProjectConfiguration<NoVerifyConfiguration> {
        return NoVerifyProjectConfiguration.getInstance(project)
    }

    override fun createConfiguration(): NoVerifyConfiguration {
        return NoVerifyConfiguration()
    }

    override fun getInspectionId(): String {
        return "NoVerifyGlobal"
    }

    override fun getHelpTopic(): String {
        return "reference.settings.php.NoVerify"
    }

    override fun getGlobalTool(project: Project, profile: InspectionProfile?): QualityToolValidationGlobalInspection? {
        val newProfile = profile ?: InspectionProjectProfileManager.getInstance(project).currentProfile

        val inspectionTool = newProfile.getInspectionTool(inspectionId, project) ?: return null

        return tryCast(inspectionTool.tool, NoVerifyGlobalInspection::class.java)
    }

    override fun getInspectionShortName(project: Project): String {
        val tool = getGlobalTool(project, null)

        return tool?.shortName ?: inspection.shortName
    }

    companion object {
        val INSTANCE = NoVerifyQualityToolType()
    }
}
