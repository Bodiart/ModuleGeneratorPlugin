package com.bodiart.data.file

import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.bodiart.model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage

interface Directory {

    fun findSubdirectory(name: String): Directory?
    fun findFile(name: String): PsiFile?
    fun fileExist(name: String): Boolean
    fun createSubdirectory(name: String): Directory
    fun addFile(file: File)
}

class DirectoryImpl(private val project: Project,
                    private val psiDirectory: PsiDirectory) : Directory {

    override fun findSubdirectory(name: String) = psiDirectory.findSubdirectory(name)?.let { DirectoryImpl(project, it) }

    override fun findFile(name: String): PsiFile? = psiDirectory.findFile(name)

    override fun fileExist(name: String): Boolean = findFile(name) != null

    override fun createSubdirectory(name: String) = DirectoryImpl(project, psiDirectory.createSubdirectory(name))

    override fun addFile(file: File) {
        val language = when(file.fileType) {
            FileType.KOTLIN -> KotlinLanguage.INSTANCE
            FileType.LAYOUT_XML -> XMLLanguage.INSTANCE
        }
        val psiFile = PsiFileFactory.getInstance(project).createFileFromText("${file.name}.${file.fileType.extension}", language, file.content)
        psiDirectory.add(psiFile)
    }
}
