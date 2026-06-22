package com.edukaiquedev.android_app

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.edukaiquedev.android_app.api.RetrofitClient
import com.edukaiquedev.android_app.api.VotoRequest
import com.edukaiquedev.android_app.api.VotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmarVotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_voto)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Confirmar Voto"

        val oscarPrefs  = getSharedPreferences("oscar_prefs", MODE_PRIVATE)
        val votosPrefs  = getSharedPreferences("votos", MODE_PRIVATE)

        val idFilme     = oscarPrefs.getString("voto_filme_id", null)
        val nomeFilme   = oscarPrefs.getString("voto_filme_nome", null)
        val nomeDiretor = votosPrefs.getString("diretor_escolhido", null)
        val jaConfirmado = oscarPrefs.getBoolean("voto_confirmado", false)

        val tvNomeFilme   = findViewById<TextView>(R.id.tv_nome_filme)
        val tvNomeDiretor = findViewById<TextView>(R.id.tv_nome_diretor)
        val etToken       = findViewById<EditText>(R.id.et_token)
        val btnConfirmar  = findViewById<Button>(R.id.btn_confirmar)

        tvNomeFilme.text   = nomeFilme   ?: "Nenhum filme selecionado"
        tvNomeDiretor.text = nomeDiretor ?: "Nenhum diretor selecionado"

        if (jaConfirmado) {
            entrarModoSomenteLeitura(etToken, btnConfirmar)
            return
        }

        btnConfirmar.setOnClickListener {
            val tokenDigitado = etToken.text.toString().trim()

            if (tokenDigitado.isEmpty()) {
                Toast.makeText(this, "Insira o token de votação", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (idFilme.isNullOrEmpty() || nomeDiretor.isNullOrEmpty()) {
                Toast.makeText(this, "Vote em um filme e em um diretor antes de confirmar", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val votoRequest = VotoRequest(
                idFilme   = idFilme,
                idDiretor = nomeDiretor,
                token     = tokenDigitado.toInt()
            )

            btnConfirmar.isEnabled = false
            btnConfirmar.text = "Enviando..."

            RetrofitClient.getInstancia(this).confirmarVoto(votoRequest)
                .enqueue(object : Callback<VotoResponse> {
                    override fun onResponse(call: Call<VotoResponse>, response: Response<VotoResponse>) {
                        val body = response.body()
                        if (response.isSuccessful && body?.sucesso == true) {
                            oscarPrefs.edit().putBoolean("voto_confirmado", true).apply()
                            mostrarDialog("Voto Confirmado!", body.mensagem, bloqueiaUi = true, etToken, btnConfirmar)
                        } else {
                            btnConfirmar.isEnabled = true
                            btnConfirmar.text = "Confirmar Voto"
                            val mensagemErro = when (body?.codigoErro) {
                                1 -> "Token inválido. Verifique o token exibido na tela de boas-vindas."
                                2 -> "Você já confirmou seu voto anteriormente."
                                3 -> "Usuário não encontrado."
                                else -> body?.mensagem ?: "Erro ao confirmar voto. Tente novamente."
                            }
                            mostrarDialog("Erro", mensagemErro, bloqueiaUi = body?.codigoErro == 2, etToken, btnConfirmar)
                        }
                    }

                    override fun onFailure(call: Call<VotoResponse>, t: Throwable) {
                        btnConfirmar.isEnabled = true
                        btnConfirmar.text = "Confirmar Voto"
                        mostrarDialog("Sem conexão", "Não foi possível conectar ao servidor. Verifique sua conexão.", bloqueiaUi = false, etToken, btnConfirmar)
                    }
                })
        }
    }

    private fun mostrarDialog(titulo: String, mensagem: String, bloqueiaUi: Boolean, etToken: EditText, btnConfirmar: Button) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                if (bloqueiaUi) entrarModoSomenteLeitura(etToken, btnConfirmar)
            }
            .setCancelable(false)
            .show()
    }

    private fun entrarModoSomenteLeitura(etToken: EditText, btnConfirmar: Button) {
        etToken.isEnabled = false
        etToken.setText("••••••")
        btnConfirmar.isEnabled = false
        btnConfirmar.text = "Voto já confirmado"
        supportActionBar?.subtitle = "Somente leitura"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { finish(); return true }
        return super.onOptionsItemSelected(item)
    }
}
