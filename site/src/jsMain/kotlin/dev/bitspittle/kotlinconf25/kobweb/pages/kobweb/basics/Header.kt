package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.HeaderBackground
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initHeaderPage(ctx: InitRouteContext) {
    ctx.data.add(HeaderBackground("/assets/images/cobweb.jpg"))
}

@Page
@Composable
@Layout(".components.layouts.HeaderSlideLayout")
fun HeaderPage() {
    SpanText("Kobweb", Modifier.color(SiteColors.KobwebBlue))
    Br()
    Text("Basics")
}
