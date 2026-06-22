package com.edukaiquedev.android_app

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edukaiquedev.android_app.api.Director
import com.edukaiquedev.android_app.api.RetrofitClient
import com.edukaiquedev.android_app.databinding.ActivityVoteDirectorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoteDirectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoteDirectorBinding
    private var selectedDirectorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteDirectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicia a busca de diretores assim que a tela abre
        buscarDiretores()

        // Configura o botão de confirmação
        binding.btnConfirmVote.setOnClickListener {
            if (selectedDirectorId == null) {
                Toast.makeText(this, "Por favor, selecione um diretor", Toast.LENGTH_SHORT).show()
            } else {
                // Salva o voto localmente e fecha a tela
                salvarVotoLocalmente(selectedDirectorId!!)
                Toast.makeText(this, "Voto registrado localmente!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * faz uma chamada assíncrona usando Retrofit dos diretores.
     * RetrofitClient (biblioteca Retrofit e Gson) trasnforma o JSON recebido
     * da URL /diretor.json em uma lista de objetos.
     */
    private fun buscarDiretores() {
        binding.pbLoading.visibility = View.VISIBLE
        RetrofitClient.instance.getDirectors().enqueue(object : Callback<List<Director>> {
            override fun onResponse(call: Call<List<Director>>, response: Response<List<Director>>) {
                binding.pbLoading.visibility = View.GONE
                if (response.isSuccessful) {
                    val directors = response.body()
                    if (directors != null) {
                        // Se a resposta for sucesso, monta a interface com os dados
                        preencherGrupoDeSelecao(directors)
                    }
                } else {
                    Toast.makeText(this@VoteDirectorActivity, "Erro ao carregar diretores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Director>>, t: Throwable) {
                binding.pbLoading.visibility = View.GONE
                Toast.makeText(this@VoteDirectorActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Cria dinamicamente RadioButtons dentro do RadioGroup.
     * percorre a lista de diretores recebida do JSON e cria um componente visual
     * para cada um, garantindo que o layout funcione para qualquer quantidade de diretores.
     */
    private fun preencherGrupoDeSelecao(directors: List<Director>) {
        binding.rgDirectors.removeAllViews()
        for (director in directors) {
            val radioButton = RadioButton(this)
            radioButton.text = director.name
            radioButton.id = View.generateViewId() // Gera um ID único para cada botão
            
            // Ajustes de visualização: espaçamento e tamanho do texto
            radioButton.setPadding(16, 24, 16, 24)
            radioButton.textSize = 18f

            radioButton.setOnClickListener {
                // Guarda o ID do diretor selecionado para uso posterior
                selectedDirectorId = director.id
            }
            binding.rgDirectors.addView(radioButton)
        }
    }

    /**
     * Salva a escolha do usuário no SharedPreferences.
     * SharedPreferences é um sistema de armazenamento de chave-valor simples do Android,
     * ideal para guardar preferências ou votos locais antes do envio final ao servidor.
     */
    private fun salvarVotoLocalmente(directorId: String) {
        val sharedPreferences = getSharedPreferences("OscarAppVotes", MODE_PRIVATE)
        sharedPreferences.edit().putString("voted_director_id", directorId).apply()
    }
}
