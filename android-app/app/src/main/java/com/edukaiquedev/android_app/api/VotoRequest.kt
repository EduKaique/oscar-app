package com.edukaiquedev.android_app.api

import com.google.gson.annotations.SerializedName

data class VotoRequest(
    @SerializedName("idFilme")   val idFilme: String,
    @SerializedName("idDiretor") val idDiretor: String,
    @SerializedName("token")     val token: Int
)
