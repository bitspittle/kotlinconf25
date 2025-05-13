package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.height
import com.varabyte.kobweb.compose.css.width
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.kotlinconf.KotlinConfLogo
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.*

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun ThanksPage() {
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.align(Alignment.TopStart)) {
            H6 { Text("@bitspittle.bsky.social") }
        }

        Box(
            Modifier.align(Alignment.TopEnd)
        ) {
            Image("/assets/images/app-upsell.png", scale = 0.75f)
        }

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .width(50.percent)
        ) {
            H2 {
                Text("Thank You, and Don't Forget to Vote")
            }
        }
    }
}
