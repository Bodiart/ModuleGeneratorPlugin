package com.bodiart.model

enum class AndroidComponent(val displayName: String) {
    ACTIVITY("Activity"),
    FRAGMENT("Fragment"),
    DIALOG_FRAGMENT("DialogFragment");

    override fun toString() = displayName
}