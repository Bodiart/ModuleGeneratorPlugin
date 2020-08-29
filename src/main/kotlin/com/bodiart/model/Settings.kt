package com.bodiart.model

import java.io.Serializable

private const val DEFAULT_BASE_ACTIVITY_CLASS = "androidx.appcompat.app.AppCompatActivity"
private const val DEFAULT_BASE_FRAGMENT_CLASS = "androidx.fragment.app.Fragment"
private const val DEFAULT_BASE_DIALOG_FRAGMENT_CLASS = "androidx.fragment.app.DialogFragment"
private val DEFAULT_VIEW_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}.${Variable.PACKAGE_MODULE_NAME.value}.view\n\nimport ${Variable.ANDROID_COMPONENT_FULL_CLASS_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : ${Variable.ANDROID_COMPONENT_CLASS_NAME.value}"
private val DEFAULT_CONTRACT_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}.${Variable.PACKAGE_MODULE_NAME.value}.contract\n\ninterface ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private val DEFAULT_PRESENTER_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}.${Variable.PACKAGE_MODULE_NAME.value}.presenter\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

private fun defaultScreenElements() = mutableListOf(
        ScreenElement(SCREEN_ELEMENT_NAME_VIEW, DEFAULT_VIEW_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}"),
        ScreenElement(SCREEN_ELEMENT_NAME_PRESENTER, DEFAULT_PRESENTER_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement(SCREEN_ELEMENT_NAME_CONTRACT, DEFAULT_CONTRACT_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement(SCREEN_ELEMENT_NAME_LAYOUT, FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
)

data class Settings(var screenElements: MutableList<ScreenElement> = defaultScreenElements(),
                    var activityBaseClass: String = DEFAULT_BASE_ACTIVITY_CLASS,
                    var fragmentBaseClass: String = DEFAULT_BASE_FRAGMENT_CLASS,
                    var dialogFragmentBaseClass: String = DEFAULT_BASE_DIALOG_FRAGMENT_CLASS
) : Serializable

const val SCREEN_ELEMENT_NAME_VIEW = "View"
const val SCREEN_ELEMENT_NAME_PRESENTER = "Presenter"
const val SCREEN_ELEMENT_NAME_CONTRACT = "Contract"
const val SCREEN_ELEMENT_NAME_LAYOUT = "layout"

const val SCREEN_ELEMENT_PACKAGE_NAME_VIEW = "view"
const val SCREEN_ELEMENT_PACKAGE_NAME_PRESENTER = "presenter"
const val SCREEN_ELEMENT_PACKAGE_NAME_CONTRACT = "contract"