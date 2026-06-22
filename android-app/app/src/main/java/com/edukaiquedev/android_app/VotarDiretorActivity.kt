package com.edukaiquedev.android_app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.api.Diretor
import com.edukaiquedev.android_app.api.DiretorRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VotarDiretorActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var btnConfirmar: Button
    private lateinit var radioGroupDiretores: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votar_diretor)
        progressBar = findViewById(R.id.progressBar)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        radioGroupDiretores = findViewById(R.id.radioGroupDiretores)

        carregarDiretores()

        btnConfirmar.setOnClickListener {
            confirmarVoto()
        }
    }

    private fun carregarDiretores() {
        progressBar.visibility = View.VISIBLE
        // Botão desabilitado enquanto a lista não carrega para evitar confirmação sem opções
        btnConfirmar.isEnabled = false

        DiretorRetrofitClient.instancia.buscarDiretores().enqueue(object : Callback<List<Diretor>> {

            override fun onResponse(call: Call<List<Diretor>>, response: Response<List<Diretor>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val diretores = response.body() ?: emptyList()
                    montarRadioGroup(diretores)
                    btnConfirmar.isEnabled = true
                } else {
                    Toast.makeText(
                        this@VotarDiretorActivity,
                        "Erro ao carregar diretores",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Diretor>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@VotarDiretorActivity,
                    "Sem conexão com o servidor",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun montarRadioGroup(diretores: List<Diretor>) {
        // Remove views anteriores para evitar duplicatas caso o método seja chamado novamente
        radioGroupDiretores.removeAllViews()

        diretores.forEach { diretor ->
            val opcao = RadioButton(this)
            // ID gerado dinamicamente para que o RadioGroup identifique qual botão está marcado
            opcao.id = View.generateViewId()
            opcao.text = diretor.nome
            opcao.textSize = 16f
            // Padding vertical maior facilita o toque em telas pequenas
            opcao.setPadding(8, 24, 8, 24)
            radioGroupDiretores.addView(opcao)
        }
    }

    private fun confirmarVoto() {
        val idSelecionado = radioGroupDiretores.checkedRadioButtonId

        // RadioGroup retorna -1 quando nenhuma opção foi marcada
        if (idSelecionado == -1) {
            Toast.makeText(this, "Selecione um diretor antes de confirmar", Toast.LENGTH_SHORT).show()
            return
        }

        val botaoSelecionado = radioGroupDiretores.findViewById<RadioButton>(idSelecionado)
        val nomeDiretor = botaoSelecionado.text.toString()

        // SharedPreferences persiste o voto localmente; o envio ao servidor fica para etapa futura
        val preferencias = getSharedPreferences("votos", Context.MODE_PRIVATE)
        preferencias.edit()
            .putString("diretor_escolhido", nomeDiretor)
            .apply()

        Toast.makeText(this, "Voto em \"$nomeDiretor\" registrado!", Toast.LENGTH_LONG).show()

        // Bloqueia a tela após confirmação — voto não pode ser alterado localmente
        btnConfirmar.isEnabled = false
        bloquearOpcoes()
    }

    private fun bloquearOpcoes() {
        for (i in 0 until radioGroupDiretores.childCount) {
            radioGroupDiretores.getChildAt(i).isEnabled = false
        }
    }
}
