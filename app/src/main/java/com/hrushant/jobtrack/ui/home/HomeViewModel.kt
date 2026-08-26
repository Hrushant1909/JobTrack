package com.hrushant.jobtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.local.entity.Movie
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel(){

    val movies = repository.getAllMovies()
    fun initializeMovies() {

        viewModelScope.launch {

            val count = repository.getMovieCount()

            if (count == 0) {
                insertSampleMovies()
            }
        }
    }
    fun insertSampleMovies() {



        viewModelScope.launch {

            val movies = listOf(
                Movie(
                    id = 1,
                    title = "Interstellar",
                    description = "A team of explorers travels through a wormhole in space.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                    genre = "Sci-Fi",
                    rating = 8.7,
                    releaseYear = 2014
                ),

                Movie(
                    id = 2,
                    title = "Inception",
                    description = "A thief who steals secrets through dreams is given an unusual mission.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg",
                    genre = "Sci-Fi",
                    rating = 8.8,
                    releaseYear = 2010
                ),

                Movie(
                    id = 3,
                    title = "The Dark Knight",
                    description = "Batman faces a criminal mastermind who plunges Gotham into chaos.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                    genre = "Action",
                    rating = 9.0,
                    releaseYear = 2008
                )
            )

            repository.insertMovies(movies)
        }
    }

}