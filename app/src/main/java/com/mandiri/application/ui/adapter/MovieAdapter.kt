package com.mandiri.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.databinding.ViewItemMovieBinding
import com.mandiri.application.di.NetworkModule
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class MovieAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: List<Movie>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Movie>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ViewItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MovieViewHolder(
        private val binding: ViewItemMovieBinding,
        val itemClick: (Movie) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Movie) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.uiViewMoviePosterImagevuew.load(NetworkModule.BASE_URL_PIC.plus(posterPath)) {
                    transformations(RoundedCornersTransformation(8f))
                }
                binding.uiViewMovieNameTextview.text = title
                binding.uiViewMovieOverviewTextview.text = overview
                releaseDate?.let {
                    val cal = Calendar.getInstance()
                    cal.timeZone = TimeZone.getDefault()
                    cal.time = it

                    val sDf = SimpleDateFormat("dd, MMMM yyyy", Locale.getDefault())
                    val dateRelease = sDf.format(cal.time)

                    binding.uiViewMovieReleaseDateTextview.text = dateRelease
                }
            }

        }
    }

}