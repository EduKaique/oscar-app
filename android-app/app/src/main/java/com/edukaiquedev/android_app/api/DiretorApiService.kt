package com.edukaiquedev.android_app.api

import retrofit2.Call
import retrofit2.http.GET

// Retrofit gera a implementação em tempo de execução
interface DiretorApiService {

    @GET("diretor.json")
    fun buscarDiretores(): Call<List<Diretor>>
}
