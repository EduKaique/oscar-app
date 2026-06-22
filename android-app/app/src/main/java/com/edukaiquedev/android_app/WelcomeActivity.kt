package com.edukaiquedev.android_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginUsuario = intent.getStringExtra("LOGIN_USUARIO") ?: "Usuário"
        binding.tvBemVindo.text = "Bem-vindo, $loginUsuario!"
    }
}
