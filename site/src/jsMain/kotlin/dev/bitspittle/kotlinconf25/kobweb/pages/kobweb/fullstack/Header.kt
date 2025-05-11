package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.fullstack

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.HeaderBackground
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initHeaderPage(ctx: InitRouteContext) {
    ctx.data.add(HeaderBackground("/assets/images/servers.jpg"))
}

@Page
@Composable
@Layout(".components.layouts.HeaderSlideLayout")
fun HeaderPage() {
    Text("Fullstack"); Br()
    SiteColors.KobwebBlue.Text("Kobweb")
}
