package com.sdk.weatherapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.sdk.weatherapp.R
import com.sdk.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.loc)
        initViews()

    }

    private fun initViews() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false
        supportActionBar?.elevation = 0f
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_locations -> changeMenu(0, R.string.loc)
                R.id.menu_triggers -> changeMenu(1, R.string.triggers)
                R.id.menu_settings -> changeMenu(2, R.string.set)
            }
            true
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun changeMenu(index: Int, title: Int) {
        binding.viewPager.currentItem = index
        supportActionBar?.title = getString(title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}