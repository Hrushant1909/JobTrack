package com.hrushant.jobtrack.data.repository

import com.hrushant.jobtrack.data.local.dao.MovieDao
import com.hrushant.jobtrack.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDao: MovieDao
) {

    fun getAllMovies(): Flow<List<Movie>>{
        return movieDao.getAllMovies()
    }

    suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)
    }

    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

    suspend fun getMovieCount(): Int {
        return movieDao.getMovieCount()
    }

}