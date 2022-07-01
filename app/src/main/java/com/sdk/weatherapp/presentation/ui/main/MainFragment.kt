package com.sdk.weatherapp.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdk.weatherapp.R
import com.sdk.weatherapp.databinding.FragmentMainBinding
import com.sdk.weatherapp.presentation.activity.MainActivity
import com.sdk.weatherapp.presentation.activity.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_locations -> changeViewPager(0, R.string.loc)
                R.id.menu_triggers -> changeViewPager(1, R.string.triggers)
                R.id.menu_settings -> changeViewPager(2, R.string.set)
            }
            true
        }
        binding.viewPager.isUserInputEnabled = false
    }

    private fun changeViewPager(index: Int, title: Int) {
        binding.viewPager.currentItem = index
        (activity as MainActivity).supportActionBar?.title = getString(title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}