package com.e444er.cleanmovie.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {


    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExploreViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentExploreBinding.bind(view)

        viewModel = ViewModelProvider(this)[ExploreViewModel::class.java]

        binding.filter.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}