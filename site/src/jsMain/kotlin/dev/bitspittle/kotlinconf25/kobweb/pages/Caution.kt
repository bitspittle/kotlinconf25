package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun CautionPage() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        H2(Modifier.margin(bottom = 1.cssRem).toAttrs()) { Text("⚠\uFE0F Caution ⚠\uFE0F") }
        H4 {
            Bullets(Modifier.step(StepTypes.FadeUp, auto = true)) {
                Item("Not 1.0 yet", Modifier.color(SiteColors.Warning))
                RenderedItem(Modifier) {
                    SpanText("Kobweb", Modifier.color(SiteColors.KobwebBlue))
                    Text(" is ")
                    SpanText("not", Modifier.fontStyle(FontStyle.Italic))
                    Text(" a shared UI framework")
                }
            }
        }
    }
}


