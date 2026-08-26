package com.hrushant.jobtrack.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.entity.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies = emptyList<Movie>()

    fun submitList(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val ivPoster: ImageView =
            itemView.findViewById(R.id.ivPoster)
        private val tvTitle: TextView =
            itemView.findViewById(R.id.tvTitle)

        private val tvGenre: TextView =
            itemView.findViewById(R.id.tvGenre)

        private val tvRating: TextView =
            itemView.findViewById(R.id.tvRating)

        fun bind(movie: Movie) {

            tvTitle.text = movie.title

            tvGenre.text = movie.genre

            tvRating.text = "Rating: ${movie.rating}"

            Glide.with(itemView.context)
                .load(movie.posterUrl)
                .into(ivPoster)

            itemView.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }
}