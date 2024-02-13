package com.ntg.movieapiappcompose.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ntg.movieapiappcompose.R
import com.ntg.movieapiappcompose.data.model.Movie

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://image.tmdb.org/t/p/w500" + movie.backdropPath)
            .size(coil.size.Size.ORIGINAL)
            .build()
    )

    Column(
        modifier = modifier
            .padding(bottom = 24.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxSize()
            .wrapContentHeight()
            .clickable {
                onClick.invoke(movie)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (painter.state is AsyncImagePainter.State.Success) {
            Image(
                painter = painter,
                contentDescription = "cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(0.714f)
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .aspectRatio(0.714f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.onTertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = if (painter.state is AsyncImagePainter.State.Error) R.drawable.image_remove else R.drawable.image),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(horizontal = 2.dp),
            text = movie.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


    }

}