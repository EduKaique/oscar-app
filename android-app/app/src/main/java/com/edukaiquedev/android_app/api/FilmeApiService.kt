package com.edukaiquedev.android_app.api

import retrofit2.Call
import retrofit2.http.GET

interface FilmeApiService {
    @GET("filme.json")
    fun listarFilmes(): Call<List<Filme>>
}
