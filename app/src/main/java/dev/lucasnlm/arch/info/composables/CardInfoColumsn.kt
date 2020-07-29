package dev.lucasnlm.arch.info.composables

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.Column
import androidx.ui.layout.ColumnScope
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.Card
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp

@Composable
fun CardInfoColumn(name: String, children: @Composable ColumnScope.() -> Unit) {
    Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp).fillMaxWidth(),
            elevation = 2.dp
    ) {
        Column(
                modifier = Modifier.padding(16.dp)
        ) {
            Text(
                    text = name.toUpperCase(),
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
            )
            children()
        }
    }
}