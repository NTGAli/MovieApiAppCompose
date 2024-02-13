package com.ntg.movieapiappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ntg.movieapiappcompose.screen.MovieScreen
import com.ntg.movieapiappcompose.screen.MovieViewModel
import com.ntg.movieapiappcompose.screen.components.InternetErrorItem
import com.ntg.movieapiappcompose.ui.theme.MovieApiAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            MovieApiAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<MovieViewModel>()
                    val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()
                    if (movies.loadState.refresh is LoadState.Error && movies.itemCount == 0) {
                        InternetErrorItem{
                            movies.retry()
                        }
                    }else{
                        MovieScreen(movies = movies, movieViewModel = viewModel)
                    }
                }
            }
        }
    }

}