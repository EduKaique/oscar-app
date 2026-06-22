package com.edukaiquedev.android_app.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("mensagem")      val mensagem: String,
    @SerializedName("sucesso")       val sucesso: Boolean,
    @SerializedName("token")         val token: String?,
    @SerializedName("tokenVotacao")  val tokenVotacao: Int?
)
