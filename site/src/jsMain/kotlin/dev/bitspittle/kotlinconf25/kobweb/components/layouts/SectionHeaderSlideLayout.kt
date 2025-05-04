package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H1

@Layout(".components.layouts.SlideLayout")
@Composable
fun SectionHeaderSlideLayout(content: @Composable () -> Unit) {
    Box(
        Modifier.fillMaxSize().textAlign(TextAlign.Center).padding(7.cssRem),
        contentAlignment = Alignment.TopCenter
    ) {
        H1(Modifier.width(Width.MaxContent).toAttrs()) {
            content()
        }
    }
}