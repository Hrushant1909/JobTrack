package com.hrushant.jobtrack.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hrushant.jobtrack.data.local.entity.TVShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowDao {

    @Query("SELECT * FROM tv_shows")
    fun getAllTVShows(): Flow<List<TVShow>>

    @Query("SELECT * FROM tv_shows WHERE category = :category")
    fun getTVShowsByCategory(category: String): Flow<List<TVShow>>

    @Query("SELECT * FROM tv_shows WHERE genre = :genre")
    fun getTVShowsByGenre(genre: String): Flow<List<TVShow>>

    @Query("SELECT * FROM tv_shows WHERE id = :showId")
    suspend fun getTVShowById(showId: Int): TVShow?

    @Insert
    suspend fun insertTVShow(tvShow: TVShow)

    @Insert
    suspend fun insertTVShows(tvShows: List<TVShow>)

    @Query("SELECT COUNT(*) FROM tv_shows")
    suspend fun getTVShowCount(): Int
}