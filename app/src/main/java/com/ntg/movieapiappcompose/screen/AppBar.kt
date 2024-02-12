package com.ntg.movieapiappcompose.screen

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){

    CenterAlignedTopAppBar(title = {

        Text(
            "Centered Top App Bar",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    })

}