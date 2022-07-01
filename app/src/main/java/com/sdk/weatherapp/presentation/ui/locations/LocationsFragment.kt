package com.sdk.weatherapp.presentation.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.sdk.weatherapp.R
import com.sdk.weatherapp.databinding.FragmentLocationsBinding
import com.sdk.weatherapp.presentation.activity.MainActivity

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        val actionBar = (activity as MainActivity).supportActionBar!!
        if (!actionBar.isShowing) {
            actionBar.show()
        }
    }

    private fun setup() {
        locationAdapter = LocationAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = locationAdapter
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.all))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.favorite))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position

                if (tab.position == 0) {
                    binding.fabBtn.show()
                } else {
                    binding.fabBtn.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
        binding.fabBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_mapsFragment)
        }
    }
}