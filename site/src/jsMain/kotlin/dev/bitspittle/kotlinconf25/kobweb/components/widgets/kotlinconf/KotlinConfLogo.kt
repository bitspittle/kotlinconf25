package dev.bitspittle.kotlinconf25.kobweb.components.widgets.kotlinconf

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.functions.url
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.backgroundSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.Div

val KotlinConfLogoStyle = CssStyle.base {
    Modifier
        .backgroundImage(url(BasePath.prependTo("/favicon.svg")))
        .backgroundSize(BackgroundSize.Cover)
        .fillMaxSize()

}

@Composable
fun KotlinConfLogo(modifier: Modifier = Modifier) {
    Div(modifier.toAttrs()) {
        Div(KotlinConfLogoStyle.toModifier().toAttrs())
    }
}