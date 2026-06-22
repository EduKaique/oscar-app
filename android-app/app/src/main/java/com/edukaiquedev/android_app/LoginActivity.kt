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
        progressBar = findViewById(R.id.progressBar)

        btnEntrar.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            // Bloqueia envio com campos em branco antes de abrir conexão
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
        RetrofitClient.instancia.login(requisicao).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                btnEntrar.isEnabled = true
                progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                    // Passa o login para exibir na tela de boas-vindas
                    intent.putExtra("LOGIN_USUARIO", login)
                    startActivity(intent)
                    // finish() remove a LoginActivity da pilha; o botão voltar não retorna ao login
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
