package com.ntg.movieapiappcompose.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ntg.movieapiappcompose.data.local.MovieEntity
import com.ntg.movieapiappcompose.data.model.Movie
import timber.log.Timber


fun Long?.orDefault() = this ?: 0L

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


fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
