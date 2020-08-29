package com.bodiart.data.repository

import com.bodiart.data.file.ProjectStructure

interface ModuleRepository {

    fun getAllModules(): List<String>
}

class ModuleRepositoryImpl(private val projectStructure: ProjectStructure) : ModuleRepository {

    override fun getAllModules() = projectStructure.getAllModules().filter { !it.contains(projectStructure.getProjectName()) }
}