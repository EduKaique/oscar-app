package com.edukaiquedev.android_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Retrofit configurado para se comunicar com a API Spring Boot.
 * O Retrofit é uma biblioteca que transforma a interface de API em uma implementação real,
 * lidando com chamadas HTTP e conversão de JSON (usando Gson) automaticamente.
 */
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON para Objeto Kotlin
            .build()
        retrofit.create(ApiService::class.java)
    }
}
