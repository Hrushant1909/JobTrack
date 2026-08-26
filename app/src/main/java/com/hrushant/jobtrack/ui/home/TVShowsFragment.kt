package com.hrushant.jobtrack.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.database.DatabaseProvider
import com.hrushant.jobtrack.data.repository.TVShowRepository
import kotlinx.coroutines.launch

class TVShowsFragment : Fragment(R.layout.fragment_t_v_shows) {

    private val viewModel: TVShowViewModel by viewModels {

        val database =
            DatabaseProvider.getDatabase(requireContext())

        val tvShowDao =
            database.tvShowDao()

        val repository =
            TVShowRepository(tvShowDao)

        TVShowViewModelFactory(repository)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initializeShows()

        val recyclerTrendingShows =
            view.findViewById<RecyclerView>(R.id.recyclerTrendingShows)

        val recyclerPopularShows =
            view.findViewById<RecyclerView>(R.id.recyclerPopularShows)

        val recyclerSciFiShows =
            view.findViewById<RecyclerView>(R.id.recyclerSciFiShows)

        val trendingAdapter = TVShowAdapter()
        val popularAdapter = TVShowAdapter()
        val sciFiAdapter = TVShowAdapter()

        recyclerTrendingShows.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerPopularShows.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerSciFiShows.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerTrendingShows.adapter = trendingAdapter
        recyclerPopularShows.adapter = popularAdapter
        recyclerSciFiShows.adapter = sciFiAdapter

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.trendingShows.collect { shows ->

                trendingAdapter.submitList(shows)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.popularShows.collect { shows ->

                popularAdapter.submitList(shows)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.sciFiShows.collect { shows ->

                sciFiAdapter.submitList(shows)
            }
        }
    }
}