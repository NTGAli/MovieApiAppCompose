package com.ntg.movieapiappcompose.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ntg.movieapiappcompose.data.model.Movie

@Composable
fun MovieScreen(
    movies: LazyPagingItems<Movie>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (movies.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

    val errorState =
        movies.loadState.refresh is LoadState.Error || movies.loadState.append is LoadState.Error || movies.loadState.prepend is LoadState.Error

    val loadingState =
        movies.loadState.refresh is LoadState.Loading || movies.loadState.append is LoadState.Loading || movies.loadState.prepend is LoadState.Loading

    LazyVerticalGrid(columns = GridCells.Fixed(3), horizontalArrangement = Arrangement.spacedBy(
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

        items(1, span = { GridItemSpan(3) }) {
            if (loadingState) {
                CircularProgressIndicator()
            }
        }

        items(1, span = { GridItemSpan(3) }) {
            if (errorState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.error)
                )
            }
        }


    })
}