package com.hrushant.jobtrack.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hrushant.jobtrack.data.local.entity.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Insert
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE genre = :genre")
    fun getMoviesByGenre(genre: String): Flow<List<Movie>>

    @Query("""
    UPDATE movies
    SET isInMyList = :isInMyList
    WHERE id = :movieId
""")
    suspend fun updateMyListStatus(
        movieId: Int,
        isInMyList: Boolean
    )

    @Query("SELECT * FROM movies WHERE isInMyList = 1")
    fun getMyListMovies(): Flow<List<Movie>>

}