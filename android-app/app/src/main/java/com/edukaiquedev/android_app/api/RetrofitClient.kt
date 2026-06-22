package com.edukaiquedev.android_app.api

import android.content.Context
import com.edukaiquedev.android_app.BuildConfig
import com.edukaiquedev.android_app.util.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun getInstancia(context: Context): ApiService {
        val token = TokenManager(context.applicationContext).obterToken()
        val client = OkHttpClient.Builder().apply {
            if (token != null) addInterceptor(AuthInterceptor(token))
        }.build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
