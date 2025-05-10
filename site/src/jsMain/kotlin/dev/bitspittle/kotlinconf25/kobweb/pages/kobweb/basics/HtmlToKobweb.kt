@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.grid
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.pages.html.AppStyleSheet
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@InitRoute
fun initHtmlToKobwebPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        SiteColors.HtmlOrange.Text("HTML")
        Text(" to ")
        SiteColors.KobwebBlue.Text("Kobweb")
    })
}

// .red-rect {
//   width: 400px;
//   height: 200px;
//   background-color: red;
//   border-radius: 10px;
// }

val RedRectStyle = CssStyle {
    base {
        Modifier
            .width(400.px)
            .height(200.px)
            .backgroundColor(Colors.Red)
            .borderRadius(10.px)
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun HtmlToKobwebPage() {

    // Super basic HTML
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                // language=html
                """
                    <div
                        id="example"
                        style="
                            width:400px; height:200px;
                            background-color:red; border-radius:10px;
                        "
                    >
                    """.trimIndent(),
                lang = "html",
            )


//            Div(attrs = {
//                id("example")
//                style {
//                    width(400.px)
//                    height(200.px)
//                    backgroundColor(Color.red)
//                    borderRadius(10.px)
//                }
//            })
            Div(
                Modifier
                    .id("example")
                    .width(400.px)
                    .height(200.px)
                    .backgroundColor(Colors.Red)
                    .borderRadius(10.px)
                    .toAttrs()
            )
        }
    }

    // A little bit of CSS
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(2.cssRem)) {
                CodeBlock(
                    // language=css
                    """
                        .red-rect {
                            width: 400px;
                            height: 200px;
                            background-color: red;
                            border-radius: 10px;
                        }
                        """.trimIndent(),
                    lang = "css",
                )
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


            // Style(AppStyleSheet)
            // Div(attrs = {
            //    id("example")
            //    classes(AppStyleSheet.redRect)
            // })
            Div(RedRectStyle.toModifier().id("example").toAttrs())
        }
    }


    // Accessing the raw element
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(2.cssRem)) {
                CodeBlock(
                    // language=html
                    """
                        <div
                            id="example"
                            class="red-rect"
                        >
                        """.trimIndent(),
                    lang = "css",
                )
                CodeBlock(
                    // language=javascript
                    """
                        document.getElementById('example')
                            .style
                            .opacity = 0.5;
                        """.trimIndent(),
                    lang = "javascript",
                )
            }


//            Style(AppStyleSheet)
//            Div(attrs = {
//                id("example")
//                classes(AppStyleSheet.redRect)
//                ref { element ->
//                    element.style.opacity = "0.5"
//                    onDispose { }
//                }
//            })

            Div(RedRectStyle.toModifier().id("example").toAttrs {
                ref { element ->
                    element.style.opacity = "0.5"
                    onDispose { }
                }
            })
        }
    }
}


