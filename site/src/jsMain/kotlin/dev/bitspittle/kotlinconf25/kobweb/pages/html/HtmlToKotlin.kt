@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.shape.StippledRect
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@InitRoute
fun initHtmlToKotlinPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        SpanText("HTML", Modifier.color(SiteColors.HtmlOrange))
        Text(" to ")
        SpanText("Kotlin", Modifier.color(SiteColors.KotlinPurple))
    })
}

// .cyan-rect {
//   width: 400px;
//   height: 200px;
//   background-color: cyan;
//   border-radius: 10px;
// }

// kcHtml2

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.HtmlToKotlinPage() {

    // Super basic HTML
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                // language=html
                """
                    <div
                        id="example"
                        style="
                            width:400px; height:200px;
                            background-color:cyan; border-radius:10px;
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
            //        background-color:cyan;
            //        border-radius:10px;
            //    "
            // >

            // kcHtml1a
            StippledRect(400.px, 200.px)

        }
    }

    // A little bit of CSS
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Large)) {
                CodeBlock(
                    // language=css
                    """
                        .cyan-rect {
                            width: 400px;
                            height: 200px;
                            background-color: cyan;
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
                            class="cyan-rect"
                        >


                        """.trimIndent(),
                    lang = "html",
                )
            }

            // .cyan-rect {
            //   width: 400px;
            //   height: 200px;
            //   background-color: cyan;
            //   border-radius: 10px;
            // }
            //
            // <div
            //   id="example"
            //   class="cyan-rect"
            // >

            // kcHtml3
            StippledRect(400.px, 200.px)
        }
    }


    // Accessing the raw element
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Large)) {
                CodeBlock(
                    // language=html
                    """
                        <div
                            id="example"
                            class="cyan-rect"
                        >
                        """.trimIndent(),
                    lang = "html",
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


            // <div
            //   id="example"
            //   class="cyan-rect"
            // >
            //
            // document.getElementById('example')
            //   .style
            //   .opacity = 0.5;

            // kcHtml4
            StippledRect(400.px, 200.px)
        }
    }
}


