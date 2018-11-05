package com.fangli.mymovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fangli.data.Movie
import com.fangli.data.getThumbnailPosterUrl
import com.fangli.data.getThumbnailUrl
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var data: List<Movie> = ArrayList()
    var listener: MovieSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false),
            listener
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    class MovieHolder(itemView: View, var listener: MovieSelectedListener?) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie):Unit = with(itemView) {
            Glide.with(this).load(item.getThumbnailPosterUrl()).into(image)
            setOnClickListener {
                listener?.OnMovieSelected(item, this@MovieHolder.adapterPosition, image)
            }
        }
    }
}

interface MovieSelectedListener{
    fun OnMovieSelected(movie:Movie, position: Int, imageView: ImageView)
}