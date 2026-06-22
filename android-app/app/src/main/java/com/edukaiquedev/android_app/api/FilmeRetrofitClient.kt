package com.edukaiquedev.android_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FilmeRetrofitClient {

    private const val URL_BASE = "http://200.236.3.97/"

    val instancia: FilmeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmeApiService::class.java)
    }
}
