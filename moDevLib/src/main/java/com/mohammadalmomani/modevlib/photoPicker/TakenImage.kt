package com.mohammadalmomani.modevlib.photoPicker

import android.net.Uri
import java.io.File

data class TakenImage(
    val file: File? = null,
    val uri: Uri? = null,
    var isSelected: Boolean = true
) {
    init {
        require(file != null || uri != null) { "Either file or uri must be provided" }
    }
}