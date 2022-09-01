package com.mandiri.application.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.databinding.ViewItemReviewBinding
import com.mandiri.application.di.NetworkModule

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class ReviewAdapter(private val itemClick: (Review) -> Unit) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var items: MutableList<Review> = mutableListOf()

    fun setItems(items: List<Review>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Review>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ViewItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class ReviewViewHolder(
        private val binding: ViewItemReviewBinding,
        val itemClick: (Review) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Review) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                authorDetails?.let {
                    binding.uiViewAuthorPictImageview.load(NetworkModule.BASE_URL_PIC.plus(it.avatarPath)) {
                        transformations(CircleCropTransformation())
                    }
                    binding.uiViewAuthorNameTextview.text = it.username
                    binding.uiViewMovieReviewTextview.text = content
                }
            }

        }
    }

}