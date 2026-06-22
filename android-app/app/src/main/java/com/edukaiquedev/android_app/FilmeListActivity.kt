package com.edukaiquedev.android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.edukaiquedev.android_app.api.Filme
import com.edukaiquedev.android_app.api.FilmeRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmeListActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme_list)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerViewFilmes)

        carregarFilmes()
    }

    private fun carregarFilmes() {
        FilmeRetrofitClient.instancia.listarFilmes().enqueue(object : Callback<List<Filme>> {

            override fun onResponse(call: Call<List<Filme>>, response: Response<List<Filme>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val filmes = response.body() ?: emptyList()
                    val adapter = FilmeAdapter(filmes.toMutableList()) { filme ->
                        val intent = Intent(this@FilmeListActivity, FilmeDetalheActivity::class.java)
                        intent.putExtra("filme", filme)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                    recyclerView.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@FilmeListActivity, "Erro ao carregar filmes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Filme>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@FilmeListActivity, "Sem conexão com o servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
