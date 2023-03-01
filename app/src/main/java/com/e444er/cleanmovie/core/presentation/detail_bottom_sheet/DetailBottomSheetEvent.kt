package com.e444er.cleanmovie.core.presentation.detail_bottom_sheet

sealed class DetailBottomSheetEvent {
    object Close : DetailBottomSheetEvent()
    object Share : DetailBottomSheetEvent()
    object NavigateToDetailFragment : DetailBottomSheetEvent()
    object ClickedAddFavoriteList : DetailBottomSheetEvent()
    object ClickedAddWatchList : DetailBottomSheetEvent()
}