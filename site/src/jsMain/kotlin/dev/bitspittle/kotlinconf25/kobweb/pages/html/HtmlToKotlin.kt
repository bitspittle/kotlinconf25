@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.html

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.style
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.DefaultStyleSheet.style
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.step
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.AnimatedText
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.cssRem
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

// .red-rect {
//   width: 400px;
//   height: 200px;
//   background-color: red;
//   border-radius: 5px;
// }

object AppStyleSheet : StyleSheet() {
    val redRect by style {
        width(400.px)
        height(200.px)
        backgroundColor(Colors.Red)
        borderRadius(5.px)
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ShowcasePage() {

    // Super basic HTML
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                // language=html
                """
                    <div
                        id="example"
                        style="
                            width:400px;
                            height:200px;
                            background-color:red;
                            border-radius:5px;
                        "
                    >
                    """.trimIndent(),
                lang = "html",
            )


            // <div
            //    id="example"
            //    style="
            //        width:400px;
            //        height:200px;
            //        background-color:red;
            //        border-radius:5px;
            //    "
            // >
            Div(attrs = {
                id("example")
                style {
                    width(400.px)
                    height(200.px)
                    backgroundColor(Color.red)
                    borderRadius(5.px)
                }
            })
        }
    }

    // A little bit of CSS
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.Top) {
                CodeBlock(
                    // language=css
                    """
                        .red-rect {
                            width: 400px;
                            height: 200px;
                            background-color: red;
                            border-radius: 5px;
                        }
                        """.trimIndent(),
                    lang = "css",
                )
                Spacer()
                CodeBlock(
                    // language=html
                    """
                        
                        <div
                            id="example"
                            class="red-rect"
                        >
                                                
                        
                        """.trimIndent(),
                    lang = "html",
                )
            }


            // .red-rect {
            //   width: 400px;
            //   height: 200px;
            //   background-color: red;
            //   border-radius: 5px;
            // }
            //
            // <div
            //   id="example"
            //   class="red-rect"
            // >

            Style(AppStyleSheet)
            Div(attrs = {
                id("example")
                classes(AppStyleSheet.redRect)
            })
        }
    }
}


