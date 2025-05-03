package dev.bitspittle.kotlinconf25.kobweb.pages.cmp

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.SlideSectionHeaderLayout")
fun HtmlVsWasm1Page() {
    H1(Modifier.width(Width.MaxContent).toAttrs()) {
        Text("Compose HTML")
        SpanText(" vs", Modifier.color(Colors.Red))
        Br()
        Text("CMP For Web (Wasm)")
    }
}
