package com.vk.noverify

import com.intellij.codeInspection.InspectionProfile
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.tools.quality.QualityToolAnnotatorInfo
import com.jetbrains.php.tools.quality.QualityToolConfiguration

class NoVerifyQualityToolAnnotatorInfo(
    psiFile: PsiFile?,
    inspection: NoVerifyValidationInspection,
    profile: InspectionProfile,
    project: Project,
    configuration: QualityToolConfiguration,
    isOnTheFly: Boolean
) : QualityToolAnnotatorInfo<NoVerifyValidationInspection>(
    psiFile,
    inspection,
    profile,
    project,
    configuration,
    isOnTheFly
)
