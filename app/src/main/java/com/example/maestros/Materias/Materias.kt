package com.example.maestros.Materias

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Materias(
val name: String? = null,
val date: String? = null,
val hora: String? = null,
val description: String? = null,
val url: String? = null,
@Exclude val key: String? = null) {
}