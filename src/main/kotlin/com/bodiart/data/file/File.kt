package com.bodiart.data.file

import com.bodiart.model.FileType

data class File(val name: String, val content: String, val fileType: FileType)