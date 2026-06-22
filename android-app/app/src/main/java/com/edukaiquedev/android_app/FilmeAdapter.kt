package com.edukaiquedev.android_app

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edukaiquedev.android_app.api.Filme
import java.net.URL

class FilmeAdapter(
    private val filmes: MutableList<Filme> = mutableListOf(),
    private val onItemClick: (Filme) -> Unit
) : RecyclerView.Adapter<FilmeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFilme: ImageView = view.findViewById(R.id.img_filme)
        val tvNome: TextView = view.findViewById(R.id.tv_nome_filme)
        val tvGenero: TextView = view.findViewById(R.id.tv_genero_filme)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filme, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filme = filmes[position]
        holder.tvNome.text = filme.nome
        holder.tvGenero.text = filme.genero
        holder.itemView.setOnClickListener { onItemClick(filme) }

        holder.imgFilme.setImageBitmap(null)
        holder.imgFilme.tag = filme.foto

        Thread {
            try {
                val bmp = URL(filme.foto).openStream().use { BitmapFactory.decodeStream(it) }
                holder.imgFilme.post {
                    if (holder.imgFilme.tag == filme.foto) {
                        holder.imgFilme.setImageBitmap(bmp)
                    }
                }
            } catch (_: Exception) { }
        }.start()
    }

    override fun getItemCount() = filmes.size

    fun atualizarLista(novaLista: List<Filme>) {
        filmes.clear()
        filmes.addAll(novaLista)
        notifyDataSetChanged()
    }
}
