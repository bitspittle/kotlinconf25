package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.browser.UrlBar
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.kobweb.LiveReloadingIndicator
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initWhyPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        Text("Why did I create")
        Br()
        SiteColors.KobwebBlue.Text("Kobweb")
        Text("?")
    })
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun WhyPage() {

    @Composable
    fun Reason(title: @Composable () -> Unit, content: @Composable ColumnScope.() -> Unit) {
        SlideSection {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                H3(Modifier.color(SiteColors.OffWhite).margin(bottom = 1.cssRem).toAttrs()) { title() }
                content()
                Spacer()
            }
        }
    }

    @Composable
    fun Reason(title: String, content: @Composable ColumnScope.() -> Unit) {
        Reason({ Text(title) }, content)
    }

    Reason("Live Reloading") {
        LiveReloadingIndicator()
    }

    Reason("Routing") {
        UrlBar("https://example-site.com/[about]")
        CodeBlock(
            //language=kotlin
            """
                @Page
                @Composable
                fun AboutPage() {
                   /*...*/
                }
            """.trimIndent()
        )
    }

    Reason("SEO") {
        Spacer()
        Image("/assets/images/fluense-seo.png", scale = 2f)
    }

    Reason("Markdown Support") {
        CodeBlock(
            //language=markdown
            """
                # Title
                
                ## Subtitle
                
                Great for blog posts or about pages!
            """.trimIndent(),
            lang = "markdown",
        )
    }

    Reason(title = {
        Row(Modifier.color(SiteColors.Android), verticalAlignment = Alignment.CenterVertically) {
            FaAndroid(Modifier.margin(right = 0.3.em))
            Text("Android")
            SpanText("-ish", Modifier.fontStyle(FontStyle.Italic))
        }
    }) {
        H4 {
            Bullets(Modifier.fontFamily("monospace")) {
                Item("Modifier")
                Item("Box, Row, Column")
            }
        }
    }
}



