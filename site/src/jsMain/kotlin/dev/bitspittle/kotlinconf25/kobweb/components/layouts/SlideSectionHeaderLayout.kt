package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.percent


@Layout(".components.layouts.SlideLayout")
@Composable
fun SlideSectionHeaderLayout(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize().textAlign(TextAlign.Center).padding(20.percent), contentAlignment = Alignment.TopCenter) {
        content()
    }
}