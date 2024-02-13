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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntg.movieapiappcompose.R
import com.ntg.movieapiappcompose.screen.components.InternetErrorItem
import com.ntg.movieapiappcompose.util.Constants.Animator.LOGO_ANIMATION_DURATION
import com.ntg.movieapiappcompose.util.orZero
import com.ntg.movieapiappcompose.util.timber
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    movies: LazyPagingItems<Movie>,
    movieViewModel: MovieViewModel,
) {
    var appBarHeight by remember {
        mutableStateOf(0.dp)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()



    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBar(scrollBehavior = scrollBehavior)
        }, content = {
            appBarHeight = it.calculateTopPadding()
            Content(movies, it) {
                scope.launch {
                    snackbarHostState.showSnackbar(it.title)
                }
            }
        }, snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        })

    AnimateLogo(
        topBarHeight = appBarHeight,
        speed =
        if (movieViewModel.isAnimationStarted) 0 else LOGO_ANIMATION_DURATION,
        loading = movies.itemCount == 0
    ) {
        movieViewModel.isAnimationStarted = true
    }

}

@Composable
private fun Content(
    movies: LazyPagingItems<Movie>,
    paddingValues: PaddingValues,
    onClick: (Movie) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        MovieListsItems(movies) {
            onClick.invoke(it)
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
        movies.loadState.append is LoadState.Loading || movies.loadState.prepend is LoadState.Loading

    val spanCount =
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) LANDSCAPE_MODE_ITEM_SIZE else PORTRAIT_MODE_ITEM_SIZE

    LazyVerticalGrid(modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = PaddingValues(top = 16.dp),
        columns = GridCells.Fixed(spanCount),
        horizontalArrangement = Arrangement.spacedBy(
            4.dp, Alignment.CenterHorizontally
        ),
        content = {

            items(movies.itemCount) { index ->
                val tmdbItem = movies[index]
                tmdbItem?.let {
                    MovieItem(
                        movie = it
                    ) {
                        onClick.invoke(it)
                    }
                }
            }

            items(1, span = { GridItemSpan(spanCount) }) {
                if (loadingState) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
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

@Composable
fun AnimateLogo(topBarHeight: Dp, loading: Boolean, speed: Int, animationFinished: () -> Unit) {
    val logo = painterResource(id = R.drawable.bazaar_logo)
    val logoWidth = LocalDensity.current.run { logo.intrinsicSize.width.dp.toPx() }
    val logoHeight = LocalDensity.current.run { logo.intrinsicSize.height.dp.toPx() }
    val topHeight = LocalDensity.current.run { topBarHeight.toPx() }
    val endPadding = LocalDensity.current.run { 16.dp.toPx() }
    val progressSize = 32.dp
    val progressPx = LocalDensity.current.run { progressSize.toPx() }

    var boxHeightDp by remember {
        mutableStateOf(0.dp)
    }

    var boxWidthDp by remember {
        mutableStateOf(0.dp)
    }

    var logoOffset by remember {
        mutableStateOf(IntOffset(0, 0))
    }

    var moved by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = loading) {
        if (!moved && !loading) {
            moved = true
        }
    }

    val size by animateSizeAsState(
        targetValue = if (moved) {
            Size(logo.intrinsicSize.width.dp.value / 2, logo.intrinsicSize.height.dp.value / 2)
        } else {
            Size(logo.intrinsicSize.width.dp.value, logo.intrinsicSize.height.dp.value)
        }, label = "size",
        animationSpec = tween(
            durationMillis = speed,
            easing = LinearOutSlowInEasing
        )
    )

    val offset by animateIntOffsetAsState(
        targetValue = if (moved) {
            IntOffset(
                (boxWidthDp.value.toInt() / 2) - (logoWidth / 4).toInt() - endPadding.toInt(),
                -(boxHeightDp.value.toInt() / 2) + (logoHeight.toInt() / 4) + (((topHeight - size.height)) / 4).toInt()
            )
        } else {
            IntOffset.Zero
        },
        label = "offset",
        animationSpec = tween(
            durationMillis = speed,
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            animationFinished.invoke()
        }
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { layoutCoordinates ->
            boxWidthDp = layoutCoordinates.size.width.dp
            boxHeightDp = layoutCoordinates.size.height.dp
        }) {

        Image(modifier = Modifier
            .align(Alignment.Center)
            .height(size.height.dp)
            .width(size.width.dp)
            .height(logo.intrinsicSize.height.dp)
            .width(logo.intrinsicSize.width.dp)
            .offset {
                offset
            }
            .onGloballyPositioned { layoutCoordinates ->
                val bottomCenter =
                    layoutCoordinates.parentCoordinates?.boundsInRoot()?.bottomCenter
                logoOffset = IntOffset(
                    bottomCenter?.x
                        ?.orZero()!!
                        .toInt() - (progressPx.toInt() / 2),
                    bottomCenter.y
                        .orZero()
                        .toInt() + progressPx.toInt()
                )
            },
            painter = painterResource(id = R.drawable.bazaar_logo),
            contentDescription = "logo"
        )

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(progressSize)
                    .offset {
                        logoOffset
                    }
            )
        }

    }

}