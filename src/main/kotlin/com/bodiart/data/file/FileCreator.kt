package com.bodiart.data.file

import com.bodiart.data.repository.SettingsRepository
import com.bodiart.data.repository.SourceRootRepository
import com.bodiart.model.*
import com.bodiart.util.toSnakeCase

private const val LAYOUT_DIRECTORY = "layout"

interface FileCreator {

    fun createScreenFiles(packageName: String, screenName: String, androidComponent: AndroidComponent, module: String)
}

class FileCreatorImpl(private val settingsRepository: SettingsRepository,
                      private val sourceRootRepository: SourceRootRepository) : FileCreator {

    override fun createScreenFiles(packageName: String, screenName: String, androidComponent: AndroidComponent, module: String) {
        val codeSubdirectory = findCodeSubdirectory(packageName, module) ?: return
        if (codeSubdirectory.findSubdirectory(screenName.toSnakeCase()) != null)
            return

        val moduleDirectory = codeSubdirectory.createSubdirectory(screenName.toSnakeCase())
        val resourcesSubdirectory = findResourcesSubdirectory(module)

        settingsRepository.loadSettings().apply {
            val baseClass = getAndroidComponentBaseClass(androidComponent)
            screenElements.forEach { screenElement ->
                if (screenElement.fileType == FileType.LAYOUT_XML) {
                    val fileName = screenElement.fileName(screenName, packageName, androidComponent.displayName, baseClass)
                    if (!resourcesSubdirectory.fileExist("$fileName.xml")) {
                        val file = File(fileName, screenElement.body(screenName, packageName, androidComponent.displayName, baseClass), screenElement.fileType)
                        resourcesSubdirectory.addFile(file)
                    }
                } else {
                    val subdirectoryName = when (screenElement.name) {
                        SCREEN_ELEMENT_NAME_VIEW -> SCREEN_ELEMENT_PACKAGE_NAME_VIEW
                        SCREEN_ELEMENT_NAME_PRESENTER -> SCREEN_ELEMENT_PACKAGE_NAME_PRESENTER
                        else -> SCREEN_ELEMENT_PACKAGE_NAME_CONTRACT
                    }
                    moduleDirectory.createSubdirectory(subdirectoryName).let { subDir ->
                        val file = File(screenElement.fileName(screenName, packageName, androidComponent.displayName, baseClass), screenElement.body(screenName, packageName, androidComponent.displayName, baseClass), screenElement.fileType)
                        subDir.addFile(file)
                    }
                }
            }
        }
    }

    private fun findCodeSubdirectory(packageName: String, module: String): Directory? = sourceRootRepository.findCodeSourceRoot(module)?.run {
        var subdirectory = directory
        packageName.split(".").forEach {
            subdirectory = subdirectory.findSubdirectory(it) ?: subdirectory.createSubdirectory(it)
        }
        return subdirectory
    }

    private fun findResourcesSubdirectory(module: String) = sourceRootRepository.findResourcesSourceRoot(module).directory.run {
        findSubdirectory(LAYOUT_DIRECTORY) ?: createSubdirectory(LAYOUT_DIRECTORY)
    }

    private fun Settings.getAndroidComponentBaseClass(androidComponent: AndroidComponent) = when (androidComponent) {
        AndroidComponent.ACTIVITY -> activityBaseClass
        AndroidComponent.FRAGMENT -> fragmentBaseClass
        AndroidComponent.DIALOG_FRAGMENT -> dialogFragmentBaseClass
    }
}