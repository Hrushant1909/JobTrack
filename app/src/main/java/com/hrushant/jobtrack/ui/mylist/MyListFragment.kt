package com.hrushant.jobtrack.ui.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrushant.jobtrack.R
import com.hrushant.jobtrack.data.local.database.DatabaseProvider
import com.hrushant.jobtrack.data.repository.MovieRepository
import com.hrushant.jobtrack.ui.home.MovieAdapter
import kotlinx.coroutines.launch


class MyListFragment : Fragment() {
    private val viewModel: MyListViewModel by viewModels {

        val database =
            DatabaseProvider.getDatabase(requireContext())

        val movieDao =
            database.movieDao()

        val repository =
            MovieRepository(movieDao)

        MyListViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val recyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerViewMyList)

        val adapter = MovieAdapter { movie ->

            val bundle = Bundle().apply {
                putInt("movieId", movie.id)
            }

            findNavController().navigate(
                R.id.movieDetailsFragment,
                bundle
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.myListMovies.collect { movies ->

                adapter.submitList(movies)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
    }
}