package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.HeaderBackground
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initHeaderPage(ctx: InitRouteContext) {
    ctx.data.add(HeaderBackground("/assets/images/sunset.png"))
}

@Page
@Composable
@Layout(".components.layouts.HeaderSlideLayout")
fun HeaderPage() {
    Text("Conclusion")
}
