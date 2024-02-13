package com.ntg.movieapiappcompose.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ntg.movieapiappcompose.R
import com.ntg.movieapiappcompose.ui.theme.MdThemeLightError
import com.ntg.movieapiappcompose.util.orZero

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    ){

    Column(modifier = modifier) {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(id = R.string.discover),
                style = MaterialTheme.typography.displayLarge,
                color = MdThemeLightError
            )
        })

        if (scrollBehavior?.state?.contentOffset.orZero() < -25f) {
            Divider(Modifier.height(1.dp), color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }

}