package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Showcase"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.ShowcasePage() {
    @Composable
    fun UrlAndContent(url: String, content: @Composable () -> Unit) {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal).padding(top = Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            Link(url)
            content()
        }
    }

    SlideSection {
        UrlAndContent("https://github.com/shubhamsinghshubham777/YouTubeRedesigned") {
            Video(
                "/assets/showcase/yt-reimagined.mp4",
                Modifier.step(StepTypes.OneAtATime, auto = true),
                loop = true,
                scale = 0.6f
            )
            Video("/assets/showcase/yt-reimagined2.mp4", Modifier.step(StepTypes.OneAtATime), loop = true, scale = 0.6f)
            Image("/assets/showcase/yt-reimagined3.png", Modifier.step(StepTypes.OneAtATime), scale = 0.6f)
        }
    }
    SlideSection {
        UrlAndContent("https://kotfolio.onrender.com/") {
            Video("/assets/showcase/kotfolio.mp4", scale = 1.1f)
        }
    }
    SlideSection {
        UrlAndContent("https://kore.ayfri.com/") {
            Video("/assets/showcase/kore.mp4", scale = 1.2f)
        }
    }
    SlideSection {
        UrlAndContent("https://chess-playground.fly.dev/") {
            Video("/assets/showcase/chess-playground.mp4", scale = 1.3f)
        }
    }
    SlideSection {
        UrlAndContent("https://bitspittle.github.io/droidcon-sf-24/") {
            Video("/assets/showcase/phoenix-red-wolf.mp4")
            }
    }
    SlideSection {
        Video("/assets/showcase/rodrigo-wedding.mp4", scale = 1.1f)
    }
    SlideSection {
        UrlAndContent("https://fluen.se/en/") {
            Video("/assets/showcase/fluense.mp4", scale = 1.1f)
        }
    }
    SlideSection {
        Image(
            "/assets/showcase/presentation-title.png",
            Modifier.border(2.px, LineStyle.Solid, SiteColors.Accent).borderRadius(10.px),
            scale = 0.7f
        )
    }
}


