package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.api.LoginRequest
import com.edukaiquedev.android_app.api.LoginResponse
import com.edukaiquedev.android_app.api.RetrofitClient
import com.edukaiquedev.android_app.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntrar.setOnClickListener {
            val login = binding.etLogin.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            // Bloqueia envio com campos em branco antes de abrir conexão
            if (login.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            realizarLogin(login, senha)
        }
    }

    private fun realizarLogin(login: String, senha: String) {
        binding.btnEntrar.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        val requisicao = LoginRequest(login, senha)
        RetrofitClient.instancia.login(requisicao).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                binding.btnEntrar.isEnabled = true
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                    // Passa o login para exibir na tela de boas-vindas
                    intent.putExtra("LOGIN_USUARIO", login)
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
                binding.btnEntrar.isEnabled = true
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this@LoginActivity,
                    "Sem conexão com o servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
