package dev.bitspittle.kotlinconf25.kobweb.components.widgets.text

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.CSSColorValue

@Composable
fun KotlinText(value: String) {
    SiteColors.KotlinPurple.Text(value)
}

@Composable
fun KobwebText(value: String) {
    SiteColors.KobwebBlue.Text(value)
}

@Composable
fun HtmlText(value: String) {
    SiteColors.HtmlOrange.Text(value)
}

@Composable
fun CSSColorValue.Text(value: String) {
    SpanText(value, Modifier.color(this))
}