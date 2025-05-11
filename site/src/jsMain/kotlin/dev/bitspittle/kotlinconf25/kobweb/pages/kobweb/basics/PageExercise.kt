package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initDemoPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Let's create a page!"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun DemoPage() {
    H3 {
        Link("/demo/guestbook")
    }
}


