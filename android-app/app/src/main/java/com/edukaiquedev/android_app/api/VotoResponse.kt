package com.edukaiquedev.android_app.api

import com.google.gson.annotations.SerializedName

data class VotoResponse(
    @SerializedName("mensagem")   val mensagem: String,
    @SerializedName("sucesso")    val sucesso: Boolean,
    @SerializedName("codigoErro") val codigoErro: Int?
)
