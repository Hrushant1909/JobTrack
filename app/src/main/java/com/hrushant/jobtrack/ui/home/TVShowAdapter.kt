package com.hrushant.jobtrack.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.entity.TVShow

class TVShowAdapter : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {

    private var shows = emptyList<TVShow>()

    fun submitList(newShows: List<TVShow>) {
        shows = newShows
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TVShowViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tv_show, parent, false)

        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TVShowViewHolder,
        position: Int
    ) {
        holder.bind(shows[position])
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    class TVShowViewHolder(
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

        fun bind(show: TVShow) {

            tvTitle.text = show.title

            tvGenre.text = show.genre

            tvRating.text = "Rating: ${show.rating}"

            Glide.with(itemView.context)
                .load(show.posterUrl)
                .into(ivPoster)
        }
    }
}