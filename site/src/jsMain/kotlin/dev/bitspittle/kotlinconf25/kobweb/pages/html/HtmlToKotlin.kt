@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.toAttrs
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
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import org.w3c.dom.get

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

object AppStyleSheet : StyleSheet() {
    val cyanRect by style {
        width(400.px)
        height(200.px)
        backgroundColor(Colors.Cyan)
        borderRadius(10.px)
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.HtmlToKotlinPage() {

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
                            background-color:cyan; border-radius:10px;
                        "
                    >
                    """.trimIndent(),
                lang = "html",
            )
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.gap(Gaps.Normal)) {
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

                CodeBlock(
                    """
                    Div(attrs = {
                        id("example")
                        style {
                            width(400.px)
                            height(200.px)
                            backgroundColor(Color.cyan)
                            borderRadius(10.px)
                        }
                    })
                    """.trimIndent(),
                    preModifier = Modifier.step(StepTypes.FadeLeft, auto = true)
                )
            }

            Div(attrs = Modifier.step(StepTypes.FadeDown).toAttrs {
                id("example")
                style {
                    width(400.px)
                    height(200.px)
                    backgroundColor(Color.cyan)
                    borderRadius(10.px)
                }
            })
        }
    }

    // A little bit of CSS
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Tiny), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Tiny)) {
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
                    highlightLines = "0|1",
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
                    highlightLines = "4",
                    preModifier = Modifier.step(StepTypes.FadeLeft)
                )
            }

            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Tiny).step(StepTypes.FadeDown)) {
                CodeBlock(
                    """
                    object AppStyleSheet : StyleSheet() {
                        val cyanRect by style {
                            width(400.px)
                            height(200.px)
                            backgroundColor(Colors.Cyan)
                            borderRadius(10.px)
                        }
                    }
                    """.trimIndent(),
                    highlightLines = "0|1|2",
                )
                CodeBlock(
                    """
                    Style(AppStyleSheet)
                    Div(attrs = {
                        id("example")
                        classes(AppStyleSheet.cyanRect)
                    })
                    """.trimIndent(),
                    preModifier = Modifier.step(StepTypes.FadeLeft),
                    highlightLines = "1,4"
                )
            }
        }
    }


    // Accessing the raw element
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
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

            Style(AppStyleSheet)

            Box(Modifier.step(StepTypes.FadeDown).fillMaxWidth(80.percent)) {
                CodeBlock(
                    """
                    Style(AppStyleSheet)
                    Div(attrs = {
                        id("example")
                        classes(AppStyleSheet.cyanRect)
                    })
                """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth().step(StepTypes.OneAtATime, auto = true)
                )

                CodeBlock(
                    """
                    Style(AppStyleSheet)
                    Div(attrs = {
                        id("example")
                        classes(AppStyleSheet.cyanRect)
                    })
                    LaunchedEffect(Unit) {
                        (document.getElementById("example") as HTMLElement)
                            .style.opacity = "0.5"
                    }
                """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth().step(StepTypes.OneAtATime),
                    highlightLines = "6-9"
                )

                Box(Modifier.fillMaxWidth().step(StepTypes.OneAtATime)) {
                    CodeBlock(
                        """
                        Style(AppStyleSheet)
                        Div(attrs = {
                            id("example")
                            classes(AppStyleSheet.cyanRect)
                            ref { element ->
                                element.style.opacity = "0.5"
                                onDispose {}
                            }
                        })
                    """.trimIndent(),
                        preModifier = Modifier.fillMaxWidth(),
                        highlightLines = "5-8|5|7|3"
                    )
                }

                CodeBlock(
                    """
                        Style(AppStyleSheet)
                        Div(attrs = {
                            classes(AppStyleSheet.cyanRect)
                            ref { element ->
                                element.style.opacity = "0.5"
                                onDispose {}
                            }
                        })
                    """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth().step(StepTypes.OneAtATime),
                )

            }
        }
    }
}


