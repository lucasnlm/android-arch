package dev.lucasnlm.arch.info.composables

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.CircularProgressIndicator
import dev.lucasnlm.arch.ui.primaryAccent

@Composable
fun LoadingScreen() {
    Column(
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = primaryAccent
        )
    }
}
