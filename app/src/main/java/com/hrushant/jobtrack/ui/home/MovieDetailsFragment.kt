package com.hrushant.jobtrack.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.database.DatabaseProvider
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels {

        val database =
            DatabaseProvider.getDatabase(requireContext())

        val movieDao =
            database.movieDao()

        val repository =
            MovieRepository(movieDao)

        MovieDetailsViewModelFactory(repository)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movieId =
            arguments?.getInt("movieId") ?: -1

        viewModel.loadMovie(movieId)

        val ivMoviePoster =
            view.findViewById<ImageView>(R.id.ivMoviePoster)

        val tvDetailsTitle =
            view.findViewById<TextView>(R.id.tvDetailsTitle)

        val tvDetailsGenre =
            view.findViewById<TextView>(R.id.tvDetailsGenre)

        val tvDetailsRating =
            view.findViewById<TextView>(R.id.tvDetailsRating)

        val tvDetailsYear =
            view.findViewById<TextView>(R.id.tvDetailsYear)

        val tvDetailsDescription =
            view.findViewById<TextView>(R.id.tvDetailsDescription)

        val btnAddToMyList =
            view.findViewById<Button>(R.id.btnAddToMyList)

        btnAddToMyList.setOnClickListener {

            viewModel.updateMyListStatus(
                movieId,
                true
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.movie.collect { movie ->

                if (movie != null) {

                    tvDetailsTitle.text = movie.title

                    tvDetailsGenre.text =
                        "Genre: ${movie.genre}"

                    tvDetailsRating.text =
                        "Rating: ${movie.rating}"

                    tvDetailsYear.text =
                        "Release Year: ${movie.releaseYear}"

                    tvDetailsDescription.text =
                        movie.description

                    Glide.with(this@MovieDetailsFragment)
                        .load(movie.posterUrl)
                        .into(ivMoviePoster)
                }
            }
        }
    }

}