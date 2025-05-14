package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps

@InitRoute
fun initDocsPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Docs"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun DocsPage() {
    Column(Modifier.fillMaxSize().gap(Gaps.Normal).padding(top = Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
        Link("https://kobweb.varabyte.com/docs")
        Video("/assets/videos/kobweb-docs.mp4", scale = 1.6f, loop = true)
    }
}
