package com.ntg.movieapiappcompose.screen.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ntg.movieapiappcompose.R
import com.ntg.movieapiappcompose.ui.theme.MdThemeLightError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    CenterAlignedTopAppBar(title = {
        Text(
            stringResource(id = R.string.discover),
            style = MaterialTheme.typography.headlineLarge,
            color = MdThemeLightError
        )
    })

}