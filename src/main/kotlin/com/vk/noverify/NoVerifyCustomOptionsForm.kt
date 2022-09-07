package com.vk.noverify

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Pair
import com.intellij.ui.LanguageTextField
import com.intellij.ui.RelativeFont
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.jetbrains.php.tools.quality.QualityToolCustomSettings
import org.intellij.lang.regexp.RegExpLanguage
import java.awt.Font
import javax.swing.JComponent
import kotlin.reflect.KMutableProperty0

class NoVerifyCustomOptionsForm(
    private val project: Project,
    private val configuration: NoVerifyConfiguration,
) : QualityToolCustomSettings() {

    data class Model(
        var asKphp: Boolean = false,
        var coresCount: Int = Runtime.getRuntime().availableProcessors(),
        var excludeRegexp: String = "",
        var unusedVarRegexp: String = "",
        var cachePath: String = "",
        var customParameters: String = "",
    )

    private lateinit var centralPanel: DialogPanel
    private val model = Model()

    override fun createComponent(): JComponent {
        val maxCores = Runtime.getRuntime().availableProcessors()

        val excludeRegexpTextField = LanguageTextField(RegExpLanguage.INSTANCE, project, "", true)
        val unusedVarRegexpTextField = LanguageTextField(RegExpLanguage.INSTANCE, project, "", true)

        centralPanel = panel {
            row {
                checkBox("Interpret code as KPHP")
                    .horizontalAlign(HorizontalAlign.FILL)
                    .bindSelected(model::asKphp)
            }.bottomGap(BottomGap.SMALL)
            row("Exclude regexp:") {
                cell(excludeRegexpTextField)
                    .monospace()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .bindText(model::excludeRegexp)
            }
            row("Unused var regexp:") {
                cell(unusedVarRegexpTextField)
                    .monospace()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .bindText(model::unusedVarRegexp)
            }
            row("Cores:") {
                spinner(1..maxCores)
                    .bindIntValue(model::coresCount)
            }
            row("Cache path:") {
                textFieldWithBrowseButton(
                    "Select Cache Folder",
                    project,
                    FileChooserDescriptorFactory.createSingleFolderDescriptor()
                ) { it.path }
                    .horizontalAlign(HorizontalAlign.FILL)
                    .bindText(model::cachePath)
            }
            row("Other parameters:") {
                expandableTextField()
                    .horizontalAlign(HorizontalAlign.FILL)
                    .bindText(model::customParameters)
            }
        }

        val disposable = Disposer.newDisposable()
        centralPanel.registerValidators(disposable)
        Disposer.register(project, disposable)

        return centralPanel
    }

    override fun isModified(): Boolean {
        centralPanel.reset()
        return model.asKphp != configuration.asKphp ||
                model.coresCount != configuration.coresCount ||
                model.excludeRegexp != configuration.excludeRegexp ||
                model.unusedVarRegexp != configuration.unusedVarRegexp ||
                model.cachePath != configuration.cachePath ||
                model.customParameters != configuration.customParameters
    }

    override fun apply() {
        centralPanel.apply()

        configuration.asKphp = model.asKphp
        configuration.coresCount = model.coresCount
        configuration.excludeRegexp = model.excludeRegexp
        configuration.unusedVarRegexp = model.unusedVarRegexp
        configuration.cachePath = model.cachePath
        configuration.customParameters = model.customParameters
    }

    override fun reset() {
        model.asKphp = configuration.asKphp
        model.coresCount = configuration.coresCount
        model.excludeRegexp = configuration.excludeRegexp
        model.unusedVarRegexp = configuration.unusedVarRegexp
        model.cachePath = configuration.cachePath
        model.customParameters = configuration.customParameters

        centralPanel.reset()
    }

    override fun getDisplayName() = null

    override fun validate() = Pair.create(false, "")

    private fun <T : JComponent> Cell<T>.monospace(): Cell<T> {
        MONOSPACED_FONT.install(component)
        return this
    }

    private fun <T : LanguageTextField> Cell<T>.bindText(prop: KMutableProperty0<String>): Cell<T> {
        return bindText(prop.toMutableProperty())
    }

    private fun <T : LanguageTextField> Cell<T>.bindText(prop: MutableProperty<String>): Cell<T> {
        return bind(LanguageTextField::getText, LanguageTextField::setText, prop)
    }

    companion object {
        private val MONOSPACED_FONT = RelativeFont.NORMAL.family(Font.MONOSPACED)
    }
}
