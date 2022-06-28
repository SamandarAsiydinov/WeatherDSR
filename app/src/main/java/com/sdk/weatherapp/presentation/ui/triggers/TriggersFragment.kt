package com.sdk.weatherapp.presentation.ui.triggers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdk.weatherapp.R

class TriggersFragment : Fragment() {

    companion object {
        fun newInstance() = TriggersFragment()
    }

    private lateinit var viewModel: TriggersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_triggers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TriggersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}