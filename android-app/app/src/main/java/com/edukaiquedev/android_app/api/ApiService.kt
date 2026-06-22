package com.edukaiquedev.android_app.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun login(@Body requisicao: LoginRequest): Call<LoginResponse>
}
