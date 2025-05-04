package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.TitledSlideData
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(TitledSlideData("TODO: Showcase"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ShowcasePage() {
    @Composable
    fun CenteredBox(modifier: Modifier, content: @Composable () -> Unit) {
        Box(Modifier.fillMaxSize().then(modifier), contentAlignment = Alignment.Center) {
            H1 { content() }
        }
    }

    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Red)) { Text("1") }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Green)) { Text("2") }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Blue)) { Text("3") }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Orange)) { Text("4") }
    }
}
