package com.e444er.cleanmovie.feature_person_detail.presentation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.presentation.util.UiEvent
import com.e444er.cleanmovie.core.presentation.util.asString
import com.e444er.cleanmovie.databinding.FragmentPersonDetailBinding
import com.e444er.cleanmovie.feature_person_detail.domain.model.PersonDetail
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PersonDetailFragment : Fragment(R.layout.fragment_person_detail) {

    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPersonDetailBinding.bind(view)

        binding.txtBio.movementMethod = ScrollingMovementMethod()

        collectData()

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        _binding = binding
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { personDetailState ->
                        binding.progressBar.isVisible = personDetailState.isLoading
                        personDetailState.personDetail?.let { personDetail ->
                            bindAttributes(personDetail = personDetail)
                        }
                    }
                }
                launch {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                Snackbar.make(
                                    requireView(),
                                    event.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is UiEvent.NavigateTo -> {
                                return@collectLatest
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindAttributes(personDetail: PersonDetail) {
        val context = requireContext()
        binding.imvPerson.load(
            ImageApi.getImage(imageUrl = personDetail.profilePath)
        ) {
            listener(
                onStart = {
                    binding.imvPerson.scaleType = ImageView.ScaleType.CENTER
                },
                onSuccess = { _, _ ->
                    binding.imvPerson.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            )

            placeholder(R.drawable.loading_animate)
        }

        binding.txtPersonName.text = personDetail.name

        personDetail.deathday?.let { deathday ->
            binding.txtDateOfDeath.isVisible = true
            binding.txtDateOfDeath.text =
                context.getString(R.string.person_date_of_death_title, deathday)
        }

        binding.txtDateOfBirth.text =
            context.getString(R.string.person_date_of_birth_title, personDetail.birthday)

        if (personDetail.biography.isNotEmpty()) {
            binding.txtBio.text =
                context.getString(R.string.person_detail_title, personDetail.biography)
        } else {
            binding.txtBio.isVisible = false
        }

        binding.txtNation.text =
            context.getString(R.string.person_nation_title, personDetail.placeOfBirth)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}