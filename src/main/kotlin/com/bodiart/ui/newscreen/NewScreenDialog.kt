package com.bodiart.ui.newscreen

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.bodiart.data.file.*
import com.bodiart.data.repository.ModuleRepositoryImpl
import com.bodiart.data.repository.SettingsRepositoryImpl
import com.bodiart.data.repository.SourceRootRepositoryImpl
import com.bodiart.model.AndroidComponent
import javax.swing.JComponent

class NewScreenDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true), NewScreenView {

    private val panel = NewScreenPanel()

    private val presenter: NewScreenPresenter

    init {
        val projectStructure = ProjectStructureImpl(project)
        val sourceRootRepository = SourceRootRepositoryImpl(projectStructure)
        val fileCreator = FileCreatorImpl(SettingsRepositoryImpl(project), sourceRootRepository)
        val packageExtractor = PackageExtractorImpl(currentPath, sourceRootRepository)
        val writeActionDispatcher = WriteActionDispatcherImpl(project)
        val moduleRepository = ModuleRepositoryImpl(projectStructure)
        presenter = NewScreenPresenter(this, fileCreator, packageExtractor, writeActionDispatcher, moduleRepository, currentPath)
        init()
    }

    override fun doOKAction() =
            presenter.onOkClick(
                    panel.packageTextField.text,
                    panel.nameTextField.text,
                    AndroidComponent.values()[panel.androidComponentComboBox.selectedIndex],
                    panel.moduleComboBox.selectedItem as String)

    override fun createCenterPanel(): JComponent {
        presenter.onLoadView()
        return panel
    }

    override fun close() = close(DialogWrapper.OK_EXIT_CODE)

    override fun showPackage(packageName: String) {
        panel.packageTextField.text = packageName
    }

    override fun showModules(modules: List<String>) = modules.forEach { panel.moduleComboBox.addItem(it) }

    override fun selectModule(module: String) {
        panel.moduleComboBox.selectedItem = module
    }
}