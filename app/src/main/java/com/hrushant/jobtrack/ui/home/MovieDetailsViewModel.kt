package com.hrushant.jobtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.local.entity.Movie
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)

    val movie: StateFlow<Movie?> = _movie

    fun loadMovie(movieId: Int) {

        viewModelScope.launch {

            _movie.value =
                repository.getMovieById(movieId)
        }
    }

    fun updateMyListStatus(
        movieId: Int,
        isInMyList: Boolean
    ) {
        viewModelScope.launch {

            repository.updateMyListStatus(
                movieId,
                isInMyList
            )
        }
    }
}