package com.mandiri.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.application.R
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.databinding.ViewItemGenreBackgroundBinding

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class GenreBgAdapter(private val itemClick: (Genre) -> Unit) :
    RecyclerView.Adapter<GenreBgAdapter.GenreViewHolder>() {
    private var items: MutableList<Genre> = mutableListOf()

    fun setItems(items: List<Genre>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Genre>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            ViewItemGenreBackgroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class GenreViewHolder(
        private val binding: ViewItemGenreBackgroundBinding,
        val itemClick: (Genre) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Genre) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.uiViewGenreTitleTextview.text =
                    itemView.context.getString(R.string.genres_name, name)
            }

        }
    }

}