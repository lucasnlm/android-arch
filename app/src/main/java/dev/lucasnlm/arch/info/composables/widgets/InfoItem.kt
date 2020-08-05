package dev.lucasnlm.arch.info.composables.widgets

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.text.style.TextAlign

@Composable
fun InfoItem(name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = name)
        Text(
            text = value,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}
