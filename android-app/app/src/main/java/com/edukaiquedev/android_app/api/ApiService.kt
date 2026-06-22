package com.edukaiquedev.android_app.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Retrofit gera a implementação em tempo de execução a partir das anotações
interface ApiService {

    @POST("login")
    fun login(@Body requisicao: LoginRequest): Call<LoginResponse>

    @POST("logout")
    fun logout(): Call<Map<String, String>>

    @POST("voto")
    fun confirmarVoto(@Body voto: VotoRequest): Call<VotoResponse>
}
