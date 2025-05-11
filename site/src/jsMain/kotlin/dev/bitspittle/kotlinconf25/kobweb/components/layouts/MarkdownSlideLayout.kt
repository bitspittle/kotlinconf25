package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobwebx.markdown.markdown
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initMarkdownSlideLayout(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle(ctx.markdown!!.frontMatter.getValue("title").single()))
}

@Layout(".components.layouts.TitledSlideLayout")
@Composable
fun MarkdownSlideLayout(content: @Composable () -> Unit) {
    Column(Modifier.fillMaxSize().gap(Gaps.Normal).padding(top = Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
        content()
    }
}