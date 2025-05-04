package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.TitledSlideLayoutData
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(TitledSlideLayoutData("TODO: Showcase"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun ShowcasePage() {
}
