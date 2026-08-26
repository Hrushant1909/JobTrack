package com.hrushant.jobtrack.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.database.DatabaseProvider
import com.hrushant.jobtrack.data.repository.MovieRepository
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    private val viewModel: HomeViewModel by viewModels {
        val database = DatabaseProvider.getDatabase(requireContext())
        val movieDao = database.movieDao()
        val repository = MovieRepository(movieDao)
        HomeViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initializeMovies()
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.movies.collect { movies ->

                println("Movies received: ${movies.size}")

            }
        }

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val adapter = HomePagerAdapter(this)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            when (position) {
                0 -> tab.text = "Movies"
                1 -> tab.text = "TV Shows"
            }

        }.attach()

    }
}