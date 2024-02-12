package com.ntg.movieapiappcompose.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ntg.movieapiappcompose.data.local.MovieEntity
import com.ntg.movieapiappcompose.data.model.Movie

@Composable
fun MovieItem(movie: Movie){

    Text(text = movie.title, Modifier.height(50.dp))

}