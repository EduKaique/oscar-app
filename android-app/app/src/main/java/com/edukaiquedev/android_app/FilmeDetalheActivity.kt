package com.edukaiquedev.android_app

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.edukaiquedev.android_app.api.Filme
import java.net.URL

class FilmeDetalheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme_detalhe)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalhes do Filme"

        @Suppress("DEPRECATION")
        val filme = intent.getSerializableExtra("filme") as? Filme
        if (filme == null) {
            finish()
            return
        }

        val imgDetalhe = findViewById<ImageView>(R.id.img_detalhe_filme)
        val tvNome = findViewById<TextView>(R.id.tv_detalhe_nome)
        val tvGenero = findViewById<TextView>(R.id.tv_detalhe_genero)
        val btnVotar = findViewById<Button>(R.id.btn_votar_filme)

        tvNome.text = filme.nome
        tvGenero.text = filme.genero

        imgDetalhe.tag = filme.foto
        Thread {
            try {
                val bmp = URL(filme.foto).openStream().use { BitmapFactory.decodeStream(it) }
                imgDetalhe.post {
                    if (imgDetalhe.tag == filme.foto) {
                        imgDetalhe.setImageBitmap(bmp)
                    }
                }
            } catch (_: Exception) { }
        }.start()

        btnVotar.setOnClickListener {
            getSharedPreferences("oscar_prefs", MODE_PRIVATE)
                .edit()
                .putString("voto_filme_id", filme.id)
                .putString("voto_filme_nome", filme.nome)
                .apply()
            Toast.makeText(this, "Voto registrado: ${filme.nome}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
