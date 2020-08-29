package com.bodiart.ui.newscreen

import com.bodiart.data.file.CurrentPath
import com.bodiart.data.file.FileCreator
import com.bodiart.data.file.PackageExtractor
import com.bodiart.data.file.WriteActionDispatcher
import com.bodiart.data.repository.ModuleRepository
import com.bodiart.model.AndroidComponent

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator,
                         private val packageExtractor: PackageExtractor,
                         private val writeActionDispatcher: WriteActionDispatcher,
                         private val moduleRepository: ModuleRepository,
                         private val currentPath: CurrentPath?) {

    fun onLoadView() {
        view.showPackage(packageExtractor.extractFromCurrentPath())
        view.showModules(moduleRepository.getAllModules())
        currentPath?.let { view.selectModule(currentPath.module) }
    }

    fun onOkClick(packageName: String, screenName: String, androidComponent: AndroidComponent, module: String) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(packageName, screenName, androidComponent, module)
        }
        view.close()
    }
}