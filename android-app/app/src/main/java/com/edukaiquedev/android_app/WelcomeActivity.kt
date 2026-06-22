package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val tvBemVindo = findViewById<TextView>(R.id.tv_bem_vindo)
        // Fallback para "Usuário" caso o extra não venha preenchido (navegação direta, testes)
        val loginUsuario = intent.getStringExtra("LOGIN_USUARIO") ?: "Usuário"
        tvBemVindo.text = "Bem-vindo, $loginUsuario!"

        val btnVotarFilmes = findViewById<Button>(R.id.btn_votar_filmes)
        btnVotarFilmes.setOnClickListener {
            startActivity(Intent(this, FilmeListActivity::class.java))
        }
    }
}
