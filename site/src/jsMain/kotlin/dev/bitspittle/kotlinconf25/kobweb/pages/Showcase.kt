package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun ShowcasePage() {
    H1 { Text("TODO: Showcase") }
}
