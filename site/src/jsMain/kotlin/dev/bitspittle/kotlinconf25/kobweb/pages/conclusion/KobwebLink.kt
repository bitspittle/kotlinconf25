package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.HeaderBackground
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initKobwebLinkPage(ctx: InitRouteContext) {
    ctx.data.add(HeaderBackground("/assets/images/cobweb2-dark.png"))
}

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
fun KobwebLinkPage() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        H3 { Text("Get out there and build your site!") }
        H4(Modifier.align(Alignment.BottomCenter).toAttrs()) { Link("https://github.com/varabyte") }
    }
}
