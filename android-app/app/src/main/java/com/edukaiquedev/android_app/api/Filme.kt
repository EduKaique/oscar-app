package com.edukaiquedev.android_app.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Filme(
    @SerializedName("id")     val id: String = "",
    @SerializedName("nome")   val nome: String = "",
    @SerializedName("genero") val genero: String = "",
    @SerializedName("foto")   val foto: String = ""
) : Serializable
