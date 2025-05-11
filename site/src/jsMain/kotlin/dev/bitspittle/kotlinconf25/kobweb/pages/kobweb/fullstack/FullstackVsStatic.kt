package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.fullstack

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initFullstackVsStaticPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        Text("Fullstack ")
        SiteColors.Error.Text("vs")
        Text(" Static")
    })
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun FullstackVsStaticPage() {
    H4 {
        Bullets {
            Item("Cost", Modifier.step(auto = true))
            Item("Speed of deployment", Modifier.step(auto = true))
            Item("Ease of deployment", Modifier.step(auto = true))
            Item("Finding a hosting solution", Modifier.step(auto = true))
            Item("Backend functionality", Modifier.step(auto = true))
        }
    }
}
