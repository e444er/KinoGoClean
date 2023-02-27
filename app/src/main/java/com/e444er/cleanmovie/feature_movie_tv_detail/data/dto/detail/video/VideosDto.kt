package com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.video

import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.squareup.moshi.Json

data class VideosDto(
    val id: Int,
    @Json(name = "results") val videoResultDto: List<VideoResultDto>
)

fun VideosDto.toVideo(): Videos {
    return Videos(
        id = id,
        result = videoResultDto.map { it.toVideoResult() }
    )
}
