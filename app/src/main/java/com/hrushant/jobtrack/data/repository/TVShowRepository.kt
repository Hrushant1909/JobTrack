package com.hrushant.jobtrack.data.repository

import com.hrushant.jobtrack.data.local.dao.TVShowDao
import com.hrushant.jobtrack.data.local.entity.TVShow
import kotlinx.coroutines.flow.Flow

class TVShowRepository(
    private val tvShowDao: TVShowDao
) {

    fun getAllTVShows(): Flow<List<TVShow>> {
        return tvShowDao.getAllTVShows()
    }

    fun getTVShowsByCategory(
        category: String
    ): Flow<List<TVShow>> {
        return tvShowDao.getTVShowsByCategory(category)
    }

    fun getTVShowsByGenre(
        genre: String
    ): Flow<List<TVShow>> {
        return tvShowDao.getTVShowsByGenre(genre)
    }

    suspend fun getTVShowById(
        showId: Int
    ): TVShow? {
        return tvShowDao.getTVShowById(showId)
    }

    suspend fun insertTVShow(
        tvShow: TVShow
    ) {
        tvShowDao.insertTVShow(tvShow)
    }

    suspend fun insertTVShows(
        tvShows: List<TVShow>
    ) {
        tvShowDao.insertTVShows(tvShows)
    }

    suspend fun getTVShowCount(): Int {
        return tvShowDao.getTVShowCount()
    }
}