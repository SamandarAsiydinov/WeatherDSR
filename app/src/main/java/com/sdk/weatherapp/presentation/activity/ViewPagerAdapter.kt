package com.sdk.weatherapp.presentation.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdk.weatherapp.presentation.ui.locations.LocationsFragment
import com.sdk.weatherapp.presentation.ui.settings.SettingsFragment
import com.sdk.weatherapp.presentation.ui.triggers.TriggersFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LocationsFragment()
            1 -> TriggersFragment()
            2 -> SettingsFragment()
            else -> Fragment()
        }
    }
}