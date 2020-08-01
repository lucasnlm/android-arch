package dev.lucasnlm.arch.info.composables

import androidx.compose.Composable
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import java.util.*

@Composable
fun InstructionItem(name: String) {
    Box(
        shape = RoundedCornerShape(5.dp),
        paddingEnd = 4.dp,
        paddingStart = 4.dp,
        paddingTop = 3.dp,
        paddingBottom = 3.dp,
        backgroundColor = Color.Red
    ) {
        Text(
            text = name.toUpperCase(Locale.getDefault()),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}
