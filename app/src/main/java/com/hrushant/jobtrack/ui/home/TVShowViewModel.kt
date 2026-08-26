package com.hrushant.jobtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrushant.jobtrack.data.local.entity.TVShow
import com.hrushant.jobtrack.data.repository.TVShowRepository
import kotlinx.coroutines.launch

class TVShowViewModel(
    private val repository: TVShowRepository
) : ViewModel() {

    val trendingShows =
        repository.getTVShowsByCategory("TRENDING")

    val popularShows =
        repository.getTVShowsByCategory("POPULAR")

    val sciFiShows =
        repository.getTVShowsByGenre("Sci-Fi")

    fun initializeShows() {

        viewModelScope.launch {

            val count = repository.getTVShowCount()

            if (count == 0) {
                insertSampleShows()
            }
        }
    }

    private fun insertSampleShows() {

        viewModelScope.launch {

            val shows = listOf(

                TVShow(
                    id = 1,
                    title = "Stranger Things",
                    description = "A group of friends uncover mysterious events in their town.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg",
                    genre = "Sci-Fi",
                    rating = 8.6,
                    releaseYear = 2016,
                    category = "TRENDING"
                ),

                TVShow(
                    id = 2,
                    title = "The Last of Us",
                    description = "A survivor and a young girl journey through a dangerous post-apocalyptic world.",
                    posterUrl = "https://m.media-amazon.com/images/I/71AavgXtd0L._AC_UF1000,1000_QL80_.jpg",
                    genre = "Drama",
                    rating = 8.6,
                    releaseYear = 2023,
                    category = "TRENDING"
                ),

                TVShow(
                    id = 3,
                    title = "Dark",
                    description = "A mysterious disappearance exposes secrets spanning several generations.",
                    posterUrl = "https://image.tmdb.org/t/p/w500/apbrbWs8M9lyOpJYU5WXrpFbk1Z.jpg",
                    genre = "Sci-Fi",
                    rating = 8.7,
                    releaseYear = 2017,
                    category = "POPULAR"
                ),

                TVShow(
                    id = 4,
                    title = "Breaking Bad",
                    description = "A chemistry teacher turns to manufacturing illegal drugs after a life-changing diagnosis.",
                    posterUrl = "https://www.tallengestore.com/cdn/shop/products/BreakingBad-BryanCranston-Heisenberg-TVShowPoster9_2ef2f86e-9ac1-4f34-998f-1b68d17ce018.jpg?v=1683604410",
                    genre = "Drama",
                    rating = 9.5,
                    releaseYear = 2008,
                    category = "POPULAR"
                )
            )

            repository.insertTVShows(shows)
        }
    }
}