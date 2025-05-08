package dev.bitspittle.kotlinconf25.kobweb.pages.html

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.AnimatedText
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        SpanText("HTML", Modifier.color(SiteColors.HtmlOrange))
        Text(" to ")
        SpanText("Kotlin", Modifier.color(SiteColors.KotlinPurple))
    })
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ShowcasePage() {
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                // language=html
                """
                    <div
                        id="example"
                        style="
                            width:200px;
                            height:100px;
                            background-color:red;
                        "
                    >
                    """.trimIndent(),
                lang = "html",
            )

//            Div(attrs = {
//                id("example")
//                style {
//                    width(200.px)
//                    height(100.px)
//                    backgroundColor(Color.red)
//                }
//            })
        }
    }
}


