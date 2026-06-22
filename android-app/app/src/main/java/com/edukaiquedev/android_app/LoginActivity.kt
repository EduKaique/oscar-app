package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.api.LoginRequest
import com.edukaiquedev.android_app.api.LoginResponse
import com.edukaiquedev.android_app.api.RetrofitClient
import com.edukaiquedev.android_app.util.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var btnEntrar: Button
    private lateinit var etLogin: EditText
    private lateinit var etSenha: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnEntrar = findViewById(R.id.btn_entrar)
        etLogin = findViewById(R.id.et_login)
        etSenha = findViewById(R.id.et_senha)
        progressBar = findViewById(R.id.progress_bar)

        btnEntrar.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (login.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            realizarLogin(login, senha)
        }
    }

    private fun realizarLogin(login: String, senha: String) {
        btnEntrar.isEnabled = false
        progressBar.visibility = View.VISIBLE

        val requisicao = LoginRequest(login, senha)
        RetrofitClient.getInstancia(this).login(requisicao).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                btnEntrar.isEnabled = true
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body()?.sucesso == true) {
                    response.body()?.token?.let { token ->
                        TokenManager(this@LoginActivity).salvarToken(token)
                    }
                    val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                    intent.putExtra("nome_usuario", login)
                    intent.putExtra("token_votacao", response.body()?.tokenVotacao ?: 0)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Usuário ou senha incorretos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                btnEntrar.isEnabled = true
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@LoginActivity,
                    "Sem conexão com o servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
