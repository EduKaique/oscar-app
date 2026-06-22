package com.edukaiquedev.android_app.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "oscar_secure_prefs",
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun salvarToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun obterToken(): String? = prefs.getString("jwt_token", null)

    fun limparToken() {
        prefs.edit().remove("jwt_token").apply()
    }
}
