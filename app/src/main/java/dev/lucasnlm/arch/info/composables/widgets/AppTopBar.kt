package dev.lucasnlm.arch.info.composables.widgets

import androidx.compose.Composable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.material.IconButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Share

@Composable
fun AppTopBar(
    title: String,
    onShare: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = onShare) {
                Icon(Icons.Filled.Share)
            }
        }
    )
}
