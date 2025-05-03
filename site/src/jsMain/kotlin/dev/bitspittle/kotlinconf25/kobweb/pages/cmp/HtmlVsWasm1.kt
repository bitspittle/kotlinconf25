package dev.bitspittle.kotlinconf25.kobweb.pages.cmp

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.KotlinText
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun HtmlVsWasm1Page() {
    H1 {
        KotlinText("Compose HTML")
        Br()
        Text("vs")
        Br()
        KotlinText("WASM")
    }
}
