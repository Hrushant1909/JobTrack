package com.hrushant.jobtrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrushant.jobtrack.data.repository.TVShowRepository

class TVShowViewModelFactory(
    private val repository: TVShowRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(TVShowViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TVShowViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}