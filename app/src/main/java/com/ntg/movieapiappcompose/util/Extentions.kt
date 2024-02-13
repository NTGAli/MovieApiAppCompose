package com.ntg.movieapiappcompose.util

import com.ntg.movieapiappcompose.data.local.MovieEntity
import com.ntg.movieapiappcompose.data.model.Movie
import timber.log.Timber


fun Long?.orDefault() = this ?: 0L
fun Float?.orZero() = this ?: 0f

fun timber(msg: String) {
    Timber.d(msg)
}


fun Movie.toEntity(): MovieEntity{
    return MovieEntity(
        id = 0,
        backdropPath = backdropPath,
        title = title,
        movieId = id.orDefault()
    )
}

fun MovieEntity.toMovie(): Movie{
    return Movie(
        id = id,
        backdropPath = backdropPath,
        title = title
    )
}