package com.ntg.movieapiappcompose.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ntg.movieapiappcompose.R

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    retry: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.internet_error),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        CustomButton(title = stringResource(id = R.string.retry)) {
            retry.invoke()
        }
    }
}