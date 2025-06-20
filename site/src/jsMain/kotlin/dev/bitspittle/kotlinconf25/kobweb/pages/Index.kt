package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.height
import com.varabyte.kobweb.compose.css.width
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
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
fun TitlePage() {
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.align(Alignment.TopStart)) {
            Row(Modifier.gap(Gaps.Minor), verticalAlignment = Alignment.CenterVertically) {
                KotlinConfLogo(Modifier.size(1.8.em))
                H4 { Text("KotlinConf 2025") }
            }
        }

        Box(
            Modifier.align(Alignment.CenterEnd)
        ) {
            Image("/assets/images/kotlinconf-line-art.png", scale = 1.2f)
        }

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .margin(bottom = 3.2.cssRem)
                .width(60.percent)
        ) {
            H2 {
                Text("Build Websites")
                Br()
                Text("in ")
                SiteColors.KotlinPurple.Text("Kotlin")
                Text(" and ")
                SiteColors.KotlinPurple.Text("Compose HTML")
                Text(" with ")
                SiteColors.KobwebBlue.Text("Kobweb")
            }
        }

        Box(Modifier.align(Alignment.BottomStart)) {
            H6 { Text("David Herman") }
        }

        Box(Modifier.align(Alignment.BottomEnd)) {
            H6 { Text("@bitspittle.bsky.social") }
        }
    }
}
