package com.edukaiquedev.android_app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.api.Diretor
import com.edukaiquedev.android_app.api.DiretorRetrofitClient
import com.edukaiquedev.android_app.databinding.ActivityVotarDiretorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VotarDiretorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVotarDiretorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVotarDiretorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carregarDiretores()

        binding.btnConfirmar.setOnClickListener {
            confirmarVoto()
        }
    }

    private fun carregarDiretores() {
        binding.progressBar.visibility = View.VISIBLE
        // Botão desabilitado enquanto a lista não carrega para evitar confirmação sem opções
        binding.btnConfirmar.isEnabled = false

        DiretorRetrofitClient.instancia.buscarDiretores().enqueue(object : Callback<List<Diretor>> {

            override fun onResponse(call: Call<List<Diretor>>, response: Response<List<Diretor>>) {
                binding.progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val diretores = response.body() ?: emptyList()
                    montarRadioGroup(diretores)
                    binding.btnConfirmar.isEnabled = true
                } else {
                    Toast.makeText(
                        this@VotarDiretorActivity,
                        "Erro ao carregar diretores",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Diretor>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
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
        binding.radioGroupDiretores.removeAllViews()

        diretores.forEach { diretor ->
            val opcao = RadioButton(this)
            // ID gerado dinamicamente para que o RadioGroup identifique qual botão está marcado
            opcao.id = View.generateViewId()
            opcao.text = diretor.nome
            opcao.textSize = 16f
            // Padding vertical maior facilita o toque em telas pequenas
            opcao.setPadding(8, 24, 8, 24)
            binding.radioGroupDiretores.addView(opcao)
        }
    }

    private fun confirmarVoto() {
        val idSelecionado = binding.radioGroupDiretores.checkedRadioButtonId

        // RadioGroup retorna -1 quando nenhuma opção foi marcada
        if (idSelecionado == -1) {
            Toast.makeText(this, "Selecione um diretor antes de confirmar", Toast.LENGTH_SHORT).show()
            return
        }

        val botaoSelecionado = findViewById<RadioButton>(idSelecionado)
        val nomeDiretor = botaoSelecionado.text.toString()

        // SharedPreferences persiste o voto localmente; o envio ao servidor fica para etapa futura
        val preferencias = getSharedPreferences("votos", Context.MODE_PRIVATE)
        preferencias.edit()
            .putString("diretor_escolhido", nomeDiretor)
            .apply()

        Toast.makeText(this, "Voto em \"$nomeDiretor\" registrado!", Toast.LENGTH_LONG).show()

        // Bloqueia a tela após confirmação — voto não pode ser alterado localmente
        binding.btnConfirmar.isEnabled = false
        bloquearOpcoes()
    }

    private fun bloquearOpcoes() {
        for (i in 0 until binding.radioGroupDiretores.childCount) {
            binding.radioGroupDiretores.getChildAt(i).isEnabled = false
        }
    }
}
