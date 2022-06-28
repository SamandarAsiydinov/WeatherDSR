package com.sdk.weatherapp.presentation.ui.locations

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdk.weatherapp.presentation.ui.all.AllFragment
import com.sdk.weatherapp.presentation.ui.favorite.FavoriteFragment

class LocationAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllFragment()
            1 -> FavoriteFragment()
            else -> Fragment()
        }
    }
}