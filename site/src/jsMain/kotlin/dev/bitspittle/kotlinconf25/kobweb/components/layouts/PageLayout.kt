package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.vh

@Layout
@Composable
fun PageLayout(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxWidth().minHeight(100.vh), contentAlignment = Alignment.Center) {
        content()
    }
}