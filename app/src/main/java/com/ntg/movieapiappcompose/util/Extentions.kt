package com.ntg.movieapiappcompose.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.ntg.movieapiappcompose.data.local.MovieEntity
import com.ntg.movieapiappcompose.data.model.Movie
import kotlinx.coroutines.launch
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
