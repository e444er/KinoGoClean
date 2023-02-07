package com.e444er.cleanmovie.presentation.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.models.enums.Categories
import com.e444er.cleanmovie.data.models.enums.Sort
import com.e444er.cleanmovie.databinding.FragmentFilterBottomSheetBinding
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Period
import com.e444er.cleanmovie.presentation.explore.ExploreViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomSheetBinding

    private val viewModel: ExploreViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        // Listen to change of the CategoriesChipGroup and update categories state in ViewModel
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val categories =
                if (group.checkedChipId == binding.movieChip.id) Categories.MOVIE else Categories.TV

            // Update categories selected state
            viewModel.setCategoryState(categories)

            // GetGenreList by categories state and language
            viewModel.getGenreListByCategoriesState()
        }

        // Listener Selected GenreList and update checkedGenreIdsState in ViewModel
        binding.genreListGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.setGenreList(checkedIds)
        }

        // Listen to change of the PeriodChipGroup and update checkedPeriod in ViewModel
        binding.periodChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.setCheckedPeriods(group.checkedChipId)
        }

        // Listen to change of the sortChipGroup and update checkedSort in ViewModel
        binding.sortChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedSort =
                if (group.checkedChipId == binding.popularity.id) Sort.Popularity else Sort.LatestRelease

            viewModel.setCheckedSortState(checkedSort)
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetFilterBottomState()
        }
    }

    private fun inflateGenreChips(chips: List<Genre>, parentChip: ChipGroup) {
        chips.forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(
                R.layout.chip, parentChip, false
            ) as Chip

            chip.text = it.name
            chip.id = it.id
            parentChip.addView(chip)
        }
    }

    private fun inflatePeriodChips(chips: List<Period>, parentChip: ChipGroup) {
        chips.forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(
                R.layout.chip, parentChip, false
            ) as Chip

            chip.text = it.time
            chip.id = it.id
            parentChip.addView(chip)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.filterBottomSheetState.collectLatest { filterBottomSheet ->

                        updateCheckCategoryFilter(filterBottomSheet.categoryState)

                        updateCheckedGenreFilters(filterBottomSheet.checkedGenreIdsState)

                        updateCheckedSortFilter(filterBottomSheet.checkedSortState)

                        updateCheckedPeriodFilter(filterBottomSheet.checkedPeriodId)
                    }
                }

                launch {
                    viewModel.isError.collectLatest {
                        if (it) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_didnt_download),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                launch {
                    viewModel.periodState.collect {
                        inflatePeriodChips(it, binding.periodChipGroup)
                    }
                }

                launch {
                    viewModel.genreList.collectLatest { genre ->
                        binding.genreListGroup.removeAllViews()
                        inflateGenreChips(chips = genre, binding.genreListGroup)
                    }
                }
            }
        }
    }

    private fun updateCheckedPeriodFilter(checkedPeriodId: Int) {
        binding.periodChipGroup.check(checkedPeriodId)
    }


    private fun updateCheckedSortFilter(checkedSortState: Sort) {
        val checkedSortId =
            if (checkedSortState == Sort.Popularity) binding.popularity.id else binding.latestRelease.id

        binding.sortChipGroup.check(checkedSortId)
    }

    private fun updateCheckedGenreFilters(checkedGenreIds: List<Int>) {

        if (checkedGenreIds.isEmpty()) {
            binding.genreListGroup.clearCheck()
            return
        }
        checkedGenreIds.forEach {
            binding.genreListGroup.check(it)
        }
    }

    private fun updateCheckCategoryFilter(categoryState: Categories) {
        val chipId = if (categoryState == Categories.MOVIE) {
            binding.movieChip.id
        } else binding.tvSeriesChip.id

        binding.categoriesChipGroup.check(chipId)
    }
}