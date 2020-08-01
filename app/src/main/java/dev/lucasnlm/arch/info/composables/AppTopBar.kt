package dev.lucasnlm.arch.info.composables

import androidx.compose.Composable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.material.IconButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Share

@Composable
fun AppTopBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Share)
            }
        }
    )
}



