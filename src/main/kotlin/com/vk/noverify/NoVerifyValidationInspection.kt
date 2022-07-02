package com.vk.noverify

import com.intellij.openapi.util.NlsSafe
import com.jetbrains.php.tools.quality.QualityToolAnnotator
import com.jetbrains.php.tools.quality.QualityToolValidationInspection
import com.vk.noverify.NoVerifyConfigurationBaseManager.Companion.NOVERIFY

class NoVerifyValidationInspection : QualityToolValidationInspection() {
    override fun getAnnotator(): QualityToolAnnotator<NoVerifyValidationInspection> {
        return NoVerifyAnnotatorProxy.INSTANCE
    }

    override fun getToolName(): @NlsSafe String {
        return NOVERIFY
    }
}
