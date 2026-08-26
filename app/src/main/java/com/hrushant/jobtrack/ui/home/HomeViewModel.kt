package com.hrushant.jobtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.local.entity.Movie
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel(){

    val trendingMovies =
        repository.getMoviesByCategory("TRENDING")

    val popularMovies =
        repository.getMoviesByCategory("POPULAR")

    val sciFiMovies =
        repository.getMoviesByGenre("Sci-Fi")
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
                    releaseYear = 2014,
                    category = "TRENDING"
                ),

                Movie(
                    id = 2,
                    title = "Inception",
                    description = "A thief who steals secrets through dreams is given an unusual mission.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/oYuLEt3zVCKq57qu2F8dT7NIa6f.jpg",
                    genre = "Sci-Fi",
                    rating = 8.8,
                    releaseYear = 2010,
                    category = "TRENDING"
                ),

                Movie(
                    id = 3,
                    title = "The Dark Knight",
                    description = "Batman faces a criminal mastermind who plunges Gotham into chaos.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                    genre = "Action",
                    rating = 9.0,
                    releaseYear = 2008,
                    category = "POPULAR"
                ),

                Movie(
                    id = 4,
                    title = "Avatar",
                    description = "A marine becomes part of a conflict on the alien world of Pandora.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
                    genre = "Sci-Fi",
                    rating = 7.9,
                    releaseYear = 2009,
                    category = "TRENDING"
                ),

                Movie(
                    id = 5,
                    title = "Dune",
                    description = "A young nobleman becomes involved in a struggle for control of a desert planet.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
                    genre = "Sci-Fi",
                    rating = 8.0,
                    releaseYear = 2021,
                    category = "TRENDING"
                ),

                Movie(
                    id = 6,
                    title = "Avengers: Endgame",
                    description = "The remaining Avengers attempt to reverse the devastating events caused by Thanos.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                    genre = "Action",
                    rating = 8.4,
                    releaseYear = 2019,
                    category = "POPULAR"
                ),

                Movie(
                    id = 7,
                    title = "The Matrix",
                    description = "A hacker discovers that reality is not what it appears to be.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                    genre = "Sci-Fi",
                    rating = 8.7,
                    releaseYear = 1999,
                    category = "POPULAR"
                )
            )

            repository.insertMovies(movies)
        }
    }

}