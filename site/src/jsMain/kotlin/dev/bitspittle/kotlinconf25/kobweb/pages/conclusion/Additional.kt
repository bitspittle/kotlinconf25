package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.dom.H4

@InitRoute
fun initAdditionalPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Additional topics"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.AdditionalPage() {
    SlideSection {
        H4 {
            Bullets {
                Item("Silk widgets", Modifier.step(auto = true))
                Item("Color mode", Modifier.step(auto = true))
                Item("Responsive design", Modifier.step(auto = true))
                Item("Transitions and animations", Modifier.step(auto = true))
                Item("Integrating a JS library", Modifier.step(auto = true))
                Item("Local and session storage", Modifier.step(auto = true))
                Item("API streams (websockets)", Modifier.step(auto = true))
                Item("Background workers", Modifier.step(auto = true))
                Item("Style variables", Modifier.step(auto = true))
            }
        }
    }
    SlideSection {
        H4 {
            Bullets {
                Item("Custom fonts", Modifier.step(auto = true))
                Item("AppGlobals", Modifier.step(auto = true))
                Item("InitSilk / InitKobweb", Modifier.step(auto = true))
                Item("Markdown handlers", Modifier.step(auto = true))
                Item("Markdown processing", Modifier.step(auto = true))
                Item("Deferred elements", Modifier.step(auto = true))
                Item("Deploying to a service", Modifier.step(auto = true))
                Item("kobweb { ... } Gradle config", Modifier.step(auto = true))
                Item("Self-hosting", Modifier.step(auto = true))
            }
        }
    }
    SlideSection {
        H4 {
            Bullets {
                Item("Multi-module builds", Modifier.step(auto = true))
                Item("Customizing the site palette", Modifier.step(auto = true))
                Item("SVG elements", Modifier.step(auto = true))
                Item("Overriding Silk styles", Modifier.step(auto = true))
                Item("Creating custom UI widgets", Modifier.step(auto = true))
                Item("Using databases on the backend", Modifier.step(auto = true))
                Item("Using your own ktor backend", Modifier.step(auto = true))
                Item("CSS tips and tricks", Modifier.step(auto = true))
                Item("Advanced browser devtools", Modifier.step(auto = true))
            }
        }
    }

}
