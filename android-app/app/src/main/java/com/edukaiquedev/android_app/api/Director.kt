package com.edukaiquedev.android_app.api

import com.google.gson.annotations.SerializedName

/**
 * O campo 'name' usa a anotação @SerializedName porque no JSON original
 * o campo se chama "nome". Isso permite que usemos nomes mais idiomáticos no Kotlin.
 */


data class Director(
    val id: String,
    @SerializedName("nome")
    val name: String
)
