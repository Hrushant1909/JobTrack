package com.hrushant.jobtrack.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            1 -> TVShowsFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}