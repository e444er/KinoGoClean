package com.e444er.cleanmovie.presentation.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentMyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment(R.layout.fragment_my_list) {


    private var binding: FragmentMyListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyListBinding.bind(view)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}