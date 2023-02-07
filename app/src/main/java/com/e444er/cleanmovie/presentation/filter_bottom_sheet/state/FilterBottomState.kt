package com.e444er.cleanmovie.presentation.filter_bottom_sheet.state

import com.e444er.cleanmovie.data.models.enums.Categories
import com.e444er.cleanmovie.data.models.enums.Sort

data class FilterBottomState(
    val categoryState: Categories = Categories.MOVIE,
    val checkedGenreIdsState: List<Int> = emptyList(),
    val checkedSortState: Sort = Sort.Popularity,
    val checkedPeriodId: Int = 0
)
