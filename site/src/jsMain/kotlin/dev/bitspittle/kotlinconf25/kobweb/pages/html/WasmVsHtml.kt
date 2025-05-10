@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.html

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initWasmVsHtmlPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        Text("CMP for Web ")
        SpanText("vs", Modifier.color(SiteColors.Error))
        Br()
        Text("Compose HTML")
    })
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun WasmVsHtmlPage() {
    Column {
        H3(Modifier.color(SiteColors.OffWhite).margin(bottom = 0.8.cssRem).toAttrs()) {
            Text("Why Compose HTML?")
        }
        Bullets {
            Item("Avoid large canvas buffer", Modifier.step(auto = true))
            Item("Smaller site download size", Modifier.step(auto = true))
            Item("Browser devtools", Modifier.step(auto = true))
            Item("Browser accessibility", Modifier.step(auto = true))
            Item("Special CSS styles (e.g. visited links)", Modifier.step(auto = true))
            Item("Compatibility with JS frameworks", Modifier.step(auto = true))
            Item("International fonts", Modifier.step(auto = true))
        }
    }
}


