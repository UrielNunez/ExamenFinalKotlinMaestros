package com.example.maestros.Alumnos

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Alumnos(
    val name: String? = null,
    val semestre: String? = null,
    val califica: String? = null,
    @Exclude val key: String? = null) {
}