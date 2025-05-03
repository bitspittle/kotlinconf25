package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.kotlinconf.KotlinConfLogo
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.KobwebText
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.KotlinText
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun TitlePage() {
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.align(Alignment.TopStart)) {
            Row(Modifier.gap(0.5.em), verticalAlignment = Alignment.CenterVertically) {
                KotlinConfLogo(Modifier.size(1.8.em))
                H4 { Text("KotlinConf 2025") }
            }
        }

        Box(
            Modifier
                .align(Alignment.BottomStart)
                .margin(bottom = 4.cssRem)
                .width(60.percent)
        ) {
            H1 {
                Text("Build Websites in ")
                KotlinText("Kotlin")
                Text(" and ")
                KotlinText("Compose HTML")
                Text(" with ")
                KobwebText("Kobweb")
            }
        }

        Box(Modifier.align(Alignment.BottomStart)) {
            Text("David Herman")
        }

        Box(Modifier.align(Alignment.BottomEnd)) {
            Text("@bitspittle.bsky.social")
        }
    }
}
