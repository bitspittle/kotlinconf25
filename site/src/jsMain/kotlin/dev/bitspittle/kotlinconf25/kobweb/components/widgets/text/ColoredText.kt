package dev.bitspittle.kotlinconf25.kobweb.components.widgets.text

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSColorValue

@Composable
fun CSSColorValue.Text(value: String) {
    SpanText(value, Modifier.color(this))
}