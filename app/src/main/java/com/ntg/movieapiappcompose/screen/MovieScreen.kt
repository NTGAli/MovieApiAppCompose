package com.ntg.movieapiappcompose.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ntg.movieapiappcompose.data.model.Movie
import com.ntg.movieapiappcompose.screen.components.AppBar
import com.ntg.movieapiappcompose.screen.components.ErrorItem
import com.ntg.movieapiappcompose.screen.components.MovieItem
import com.ntg.movieapiappcompose.util.Constants.ItemViews.LANDSCAPE_MODE_ITEM_SIZE
import com.ntg.movieapiappcompose.util.Constants.ItemViews.PORTRAIT_MODE_ITEM_SIZE
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntg.movieapiappcompose.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MovieScreen(
    movies: LazyPagingItems<Movie>,
    appBarHeight:@Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current
//    LaunchedEffect(key1 = movies.loadState) {
//        if (movies.loadState.refresh is LoadState.Error) {
//            Toast.makeText(
//                context,
//                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    Scaffold(topBar = {
        AppBar()
    }, content = {
        appBarHeight.invoke(it)
       Content(movies, it)
    })

}

@Composable
private fun Content(movies: LazyPagingItems<Movie>, paddingValues: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            MovieListsItems(movies) {

            }
        }
    }
}

@Composable
private fun MovieListsItems(
    movies: LazyPagingItems<Movie>, onClick: (Movie) -> Unit
) {

    val context = LocalContext.current
    val errorState =
        movies.loadState.refresh is LoadState.Error || movies.loadState.append is LoadState.Error || movies.loadState.prepend is LoadState.Error

    val loadingState =
        movies.loadState.refresh is LoadState.Loading || movies.loadState.append is LoadState.Loading || movies.loadState.prepend is LoadState.Loading

    val spanCount =
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) LANDSCAPE_MODE_ITEM_SIZE else PORTRAIT_MODE_ITEM_SIZE

    LazyVerticalGrid(modifier = Modifier.padding(horizontal = 8.dp), columns = GridCells.Fixed(spanCount), horizontalArrangement = Arrangement.spacedBy(
        4.dp, Alignment.CenterHorizontally
    ), content = {

        items(movies.itemCount) { index ->
            val tmdbItem = movies[index]
            tmdbItem?.let {
                MovieItem(
                    movie = it
                )
            }
        }

        items(1, span = { GridItemSpan(spanCount) }) {
            if (loadingState) {
                CircularProgressIndicator()
            }
        }

        items(1, span = { GridItemSpan(spanCount) }) {
            if (errorState) {
                ErrorItem(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                    movies.retry()
                }
            }
        }


    })
}