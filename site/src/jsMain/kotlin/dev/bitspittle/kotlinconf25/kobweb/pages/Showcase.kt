package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.AnimatedText
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Showcase"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ShowcasePage() {
    SlideSection {
        // Intentionally empty for first slide; give speaker time to talk before moving on
    }

    SlideSection {
        Video("/assets/showcase/yt-reimagined.mp4", scale = 0.6f)
    }
    SlideSection {
        Video("/assets/showcase/kotfolio.mp4", scale = 1.1f)
    }
    SlideSection {
        Video("/assets/showcase/kore.mp4", scale = 1.2f)
    }
    SlideSection {
        Video("/assets/showcase/chess-playground.mp4", scale = 1.3f)
    }
    SlideSection {
        Video("/assets/showcase/phoenix-red-wolf.mp4")
    }
    SlideSection {
        Video("/assets/showcase/rodrigo-wedding.mp4", scale = 1.1f)
    }
    SlideSection {
        Video("/assets/showcase/fluense.mp4", scale = 1.1f)
    }
    SlideSection {
        Video("/assets/showcase/kobweb-docs.mp4", scale = 1.3f, loop = true)
    }
    SlideSection {
        Image("/assets/showcase/presentation-title.png", scale = 0.7f)
    }
}


