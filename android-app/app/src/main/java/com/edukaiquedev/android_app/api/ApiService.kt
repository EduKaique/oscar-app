package com.edukaiquedev.android_app.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("http://200.236.3.97/diretor.json")
    fun getDirectors(): Call<List<Director>>
}
