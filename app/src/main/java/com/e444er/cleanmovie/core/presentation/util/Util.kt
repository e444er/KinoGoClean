package com.e444er.cleanmovie.core.presentation.util

import android.widget.ImageButton
import com.e444er.cleanmovie.R

class UtilFunctions {

    fun setAddWatchListIcon(
        doesAddWatchList: Boolean,
        imageButton: ImageButton,
    ) {
        if (doesAddWatchList) {
            imageButton.setImageResource(R.drawable.ic_baseline_video_library_24)
        } else {
            imageButton.setImageResource(R.drawable.outline_video_library_24)
        }
    }

    fun setAddFavoriteIcon(
        doesAddFavorite: Boolean,
        imageButton: ImageButton,
    ) {
        if (doesAddFavorite) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}