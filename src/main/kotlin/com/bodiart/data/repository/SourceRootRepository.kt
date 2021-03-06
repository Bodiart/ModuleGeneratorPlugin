package com.bodiart.data.repository

import com.bodiart.data.file.ProjectStructure
import com.bodiart.data.file.SourceRoot

interface SourceRootRepository {

    fun findCodeSourceRoot(module: String): SourceRoot?
    fun findResourcesSourceRoot(module: String): SourceRoot
}

class SourceRootRepositoryImpl(private val projectStructure: ProjectStructure) : SourceRootRepository {

    override fun findCodeSourceRoot(module: String) =
            projectStructure.findSourceRoots(module).firstOrNull {
                val pathTrimmed = it.path.removeModulePathPrefix(module)
                pathTrimmed.contains("src", true)
                        && pathTrimmed.contains("main", true)
                        && !pathTrimmed.contains("assets", true)
                        && !pathTrimmed.contains("test", true)
                        && !pathTrimmed.contains("res", true)
            }

    override fun findResourcesSourceRoot(module: String) =
            projectStructure.findSourceRoots(module).first {
                val pathTrimmed = it.path.removeModulePathPrefix(module)
                pathTrimmed.contains("src", true)
                        && pathTrimmed.contains("main", true)
                        && pathTrimmed.contains("res", true)
            }

    private fun String.removeModulePathPrefix(module: String) =
            removePrefix(projectStructure.getProjectPath() + "/" + module)
}