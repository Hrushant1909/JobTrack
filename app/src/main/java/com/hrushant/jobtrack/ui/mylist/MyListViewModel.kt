package com.hrushant.jobtrack.ui.mylist

import androidx.lifecycle.ViewModel
import com.hrushant.jobtrack.data.local.entity.Movie
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MyListViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    val myListMovies: Flow<List<Movie>> =
        repository.getMyListMovies()
}