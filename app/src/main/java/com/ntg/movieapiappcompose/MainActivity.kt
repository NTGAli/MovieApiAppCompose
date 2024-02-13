package com.ntg.movieapiappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ntg.movieapiappcompose.screen.MovieScreen
import com.ntg.movieapiappcompose.screen.MovieViewModel
import com.ntg.movieapiappcompose.ui.theme.MovieApiAppComposeTheme
import com.ntg.movieapiappcompose.util.timber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var appBarHeight by remember {
                mutableStateOf(0.dp)
            }

            MovieApiAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<MovieViewModel>()
                    val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()
                    MovieScreen(movies){
                        appBarHeight = it.calculateTopPadding()
                    }
                    CenterOffsetBox(appBarHeight)

                }
            }
        }
    }

    @Composable
    fun CenterOffsetBox(topBarHeight: Dp) {
        val logo = painterResource(id = R.drawable.bazaar_logo)
        val logoWidth = LocalDensity.current.run { logo.intrinsicSize.width.dp.toPx() }
        val logoHeight = LocalDensity.current.run { logo.intrinsicSize.height.dp.toPx() }
        val topHeight = LocalDensity.current.run { topBarHeight.toPx() }
        val endPadding = LocalDensity.current.run { 16.dp.toPx() }

        var boxHeightDp by remember {
            mutableStateOf(0.dp)
        }

        var boxWidthDp by remember {
            mutableStateOf(0.dp)
        }


        var moved by remember { mutableStateOf(false) }

        val size by animateSizeAsState(
            targetValue = if (moved) {
                Size(logo.intrinsicSize.width.dp.value/2, logo.intrinsicSize.height.dp.value/2)
            } else {
                Size(logo.intrinsicSize.width.dp.value, logo.intrinsicSize.height.dp.value)
            }, label = "size"
        )

        val offset by animateIntOffsetAsState(
            targetValue = if (moved) {
                IntOffset((boxWidthDp.value.toInt() /2) - (logoWidth/4).toInt()-endPadding.toInt(),-(boxHeightDp.value.toInt() / 2) + (logoHeight.toInt() / 4) +  (((topHeight - size.height))/4).toInt())
            } else {
                IntOffset.Zero
            },
            label = "offset"
        )



        val cc = LocalDensity.current.run { 2151.dp.toPx() }

        Box(modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                boxWidthDp = layoutCoordinates.size.width.dp
                boxHeightDp = layoutCoordinates.size.height.dp
            }) {

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(Color.Red)
                    .size(50.dp)
                    .align(Alignment.Center)
            )


            Image(modifier = Modifier
                .align(Alignment.Center)
                .height(size.height.dp)
                .width(size.width.dp)
                .height(logo.intrinsicSize.height.dp)
                .width(logo.intrinsicSize.width.dp)
                .offset {
                    offset
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    moved = !moved
                },
                painter = painterResource(id = R.drawable.bazaar_logo),
                contentDescription = "logo"
            )



        }

    }

}