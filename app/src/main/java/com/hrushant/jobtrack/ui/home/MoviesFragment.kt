package com.hrushant.jobtrack.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrushant.jobtrack.R
import kotlinx.coroutines.launch


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    ) {
        HomeViewModelFactory(
            requireParentFragment()
                .let { parent ->
                    val database =
                        com.hrushant.jobtrack.data.local.database.DatabaseProvider
                            .getDatabase(parent.requireContext())

                    val movieDao = database.movieDao()

                    com.hrushant.jobtrack.data.repository.MovieRepository(movieDao)
                }
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerTrending =
            view.findViewById<RecyclerView>(R.id.recyclerTrending)

        val recyclerPopular =
            view.findViewById<RecyclerView>(R.id.recyclerPopular)

        val trendingAdapter = MovieAdapter()
        val popularAdapter = MovieAdapter()

        recyclerTrending.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerPopular.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerTrending.adapter = trendingAdapter
        recyclerPopular.adapter = popularAdapter

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.movies.collect { movies ->

                trendingAdapter.submitList(movies)

                popularAdapter.submitList(movies)

            }
        }
    }

}