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
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
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
    fun Modifier.superQuickStep() = this.step(delay = AnimSpeeds.VeryQuick)

    SlideSection {
        H4 {
            Bullets {
                Item("Silk widgets", Modifier.superQuickStep())
                Item("Color mode", Modifier.superQuickStep())
                Item("Responsive design", Modifier.superQuickStep())
                Item("Transitions and animations", Modifier.superQuickStep())
                Item("Integrating a JS library", Modifier.superQuickStep())
                Item("Local and session storage", Modifier.superQuickStep())
                Item("API streams (websockets)", Modifier.superQuickStep())
                Item("Background workers", Modifier.superQuickStep())
                Item("Style variables", Modifier.superQuickStep())
            }
        }
    }
    SlideSection {
        H4 {
            Bullets {
                Item("Custom fonts", Modifier.superQuickStep())
                Item("AppGlobals", Modifier.superQuickStep())
                Item("InitSilk / InitKobweb", Modifier.superQuickStep())
                Item("Custom Markdown handlers", Modifier.superQuickStep())
                Item("Markdown processing", Modifier.superQuickStep())
                Item("Deferred elements", Modifier.superQuickStep())
                Item("Advanced layouts", Modifier.superQuickStep())
                Item("Self-hosting", Modifier.superQuickStep())
                Item("Deploying to a service", Modifier.superQuickStep())
            }
        }
    }
    SlideSection {
        H4 {
            Bullets {
                Item("Multi-module builds", Modifier.superQuickStep())
                Item("Customizing the site palette", Modifier.superQuickStep())
                Item("SVG elements", Modifier.superQuickStep())
                Item("Overriding Silk styles", Modifier.superQuickStep())
                Item("Creating custom UI widgets", Modifier.superQuickStep())
                Item("Multiplatform business logic", Modifier.superQuickStep())
                Item("Using databases on the backend", Modifier.superQuickStep())
                Item("Using your own ktor backend", Modifier.superQuickStep())
                Item("CSS tips and tricks", Modifier.superQuickStep())
                Item("Advanced browser devtools", Modifier.superQuickStep())
            }
        }
    }

}
