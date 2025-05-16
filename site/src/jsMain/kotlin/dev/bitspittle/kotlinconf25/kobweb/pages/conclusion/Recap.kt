package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.dom.H4

@InitRoute
fun initRecapPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Recap"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun RecapPage() {
    Row(Modifier.fillMaxHeight().gap(Gaps.Large), verticalAlignment = Alignment.CenterVertically) {
        H4 {
            Bullets {
                Item("Compose HTML", Modifier.step(auto = true))
                Item("Why Kobweb", Modifier.step(auto = true))
                Item("Kobweb CLI", Modifier.step(auto = true))
                Item("Layouts & Pages", Modifier.step(auto = true))
                Item("Assets", Modifier.step(auto = true))
            }
        }
        H4 {
            Bullets {
                Item("Modifiers", Modifier.step(auto = true))
                Item("CssStyle", Modifier.step(auto = true))
                Item("Routing", Modifier.step(auto = true))
                Item("Markdown", Modifier.step(auto = true))
                Item("API routes", Modifier.step(auto = true))
            }
        }
    }
}
