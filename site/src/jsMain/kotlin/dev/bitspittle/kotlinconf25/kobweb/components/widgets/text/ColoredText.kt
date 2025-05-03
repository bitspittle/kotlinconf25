package dev.bitspittle.kotlinconf25.kobweb.components.widgets.text

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors

@Composable
fun KotlinText(value: String) {
    SpanText(value, Modifier.color(SiteColors.KotlinPurple))
}

@Composable
fun KobwebText(value: String) {
    SpanText(value, Modifier.color(SiteColors.KobwebBlue))
}

@Composable
fun HtmlText(value: String) {
    SpanText(value, Modifier.color(SiteColors.HtmlOrange))
}