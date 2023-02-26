package com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.data.models.enums.Category
import com.e444er.cleanmovie.core.data.models.enums.Sort
import com.e444er.cleanmovie.core.data.models.enums.isMovie
import com.e444er.cleanmovie.core.data.models.enums.isPopularity
import com.e444er.cleanmovie.databinding.FragmentFilterBottomSheetBinding
import com.e444er.cleanmovie.feature_explore.presentation.event.ExploreBottomSheetEvent
import com.e444er.cleanmovie.feature_explore.presentation.explore.ExploreViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomSheetBinding

    lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ExploreViewModel::class.java]

        observeData()

        setViewsListener()
    }


    private fun setViewsListener() {
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val category =
                if (group.checkedChipId == binding.movieChip.id) Category.MOVIE else Category.TV

            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateCategory(category))
        }

        binding.genreListGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateGenreList(checkedIds))
        }

        binding.sortChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val checkedSort =
                if (group.checkedChipId == binding.popularity.id) Sort.Popularity else Sort.LatestRelease
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateSort(checkedSort))
        }

        binding.btnReset.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.ResetFilterBottomState)
        }

        binding.btnApply.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.Apply)
        }
    }


    private fun inflateGenreChips(
        chips: List<Genre>,
        parentChip: ChipGroup
    ) {
        chips.forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(
                R.layout.chip, parentChip, false
            ) as Chip

            chip.text = it.name
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
                    }
                }

                launch {
                    viewModel.genreList.collect { genre ->
                        binding.genreListGroup.removeAllViews()
                        inflateGenreChips(chips = genre, binding.genreListGroup)
                    }
                }

            }
        }
    }

    private fun updateCheckedSortFilter(checkedSortState: Sort) {
        val checkedSortId =
            if (checkedSortState.isPopularity()) binding.popularity.id else binding.latestRelease.id

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

    private fun updateCheckCategoryFilter(categoryState: Category) {
        val chipId = if (categoryState.isMovie()) {
            binding.movieChip.id
        } else binding.tvSeriesChip.id

        binding.categoriesChipGroup.check(chipId)
    }
}