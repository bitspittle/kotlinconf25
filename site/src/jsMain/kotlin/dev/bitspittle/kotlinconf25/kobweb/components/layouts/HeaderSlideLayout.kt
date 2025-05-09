package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextShadow
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H1

private fun Modifier.fullTextShadow(thickness: CSSLengthNumericValue = 20.px) =
    listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        .map { (x, y) ->
            TextShadow.of(x.px, y.px, thickness, Colors.Black)
        }.let { shadows ->
            this.textShadow(shadows)
        }

@Layout(".components.layouts.SlideLayout")
@Composable
fun HeaderSlideLayout(content: @Composable () -> Unit) {
    Box(
        Modifier.fillMaxSize().textAlign(TextAlign.Center).padding(5.6.cssRem),
        contentAlignment = Alignment.TopCenter
    ) {
        H1(Modifier.width(Width.MaxContent).fullTextShadow().toAttrs()) {
            content()
        }
    }
}