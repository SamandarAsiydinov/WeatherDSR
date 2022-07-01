package com.sdk.weatherapp.presentation.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sdk.weatherapp.R
import com.sdk.weatherapp.data.util.Constants
import com.sdk.weatherapp.data.util.log
import com.sdk.weatherapp.data.util.toast
import com.sdk.weatherapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    private fun initViews() {
        viewModel.getWeather("40.8154", "72.2837", Constants.API_KEY)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.response.collect {
                when(it) {
                    is ApiState.Loading -> toast("Loading")
                    is ApiState.Failure ->  {
                        log(it.msg.stackTraceToString())
                    }
                    is ApiState.Init -> Unit
                    is ApiState.Success -> {
                        log(it.data.body().toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}