package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.edukaiquedev.android_app.api.RetrofitClient
import com.edukaiquedev.android_app.util.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Oscar App"

        val nomeUsuario = intent.getStringExtra("nome_usuario") ?: "Usuário"
        val tokenVotacao = intent.getIntExtra("token_votacao", 0)
        getSharedPreferences("oscar_prefs", MODE_PRIVATE).edit()
            .putInt("token_votacao", tokenVotacao)
            .apply()

        findViewById<TextView>(R.id.tv_boas_vindas).text = "Bem-vindo, $nomeUsuario!"
        findViewById<TextView>(R.id.tv_token_valor).text = tokenVotacao.toString()

        findViewById<Button>(R.id.btn_votar_filme).setOnClickListener {
            startActivity(Intent(this, FilmeListActivity::class.java))
        }

        findViewById<Button>(R.id.btn_votar_diretor).setOnClickListener {
            startActivity(Intent(this, VotarDiretorActivity::class.java))
        }

        findViewById<Button>(R.id.btn_confirmar_voto).setOnClickListener {
            startActivity(Intent(this, ConfirmarVotoActivity::class.java))
        }

        findViewById<Button>(R.id.btn_sair).setOnClickListener {
            RetrofitClient.getInstancia(this).logout().enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                    efetuarLogout()
                }
                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    efetuarLogout()
                }
            })
        }
    }

    private fun efetuarLogout() {
        TokenManager(this).limparToken()
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
