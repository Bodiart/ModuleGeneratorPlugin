package com.bodiart.data.repository

import com.intellij.openapi.project.Project
import com.bodiart.data.ScreenGeneratorComponent
import com.bodiart.model.Settings

interface SettingsRepository {
    fun loadSettings(): Settings
    fun update(settings: Settings)
}

class SettingsRepositoryImpl(private val project: Project) : SettingsRepository {

    override fun loadSettings() = ScreenGeneratorComponent.getInstance(project).settings

    override fun update(settings: Settings) = ScreenGeneratorComponent.getInstance(project).run {
        this.settings = settings
    }
}