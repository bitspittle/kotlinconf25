@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaCodeFork
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initHtmlToKobwebPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        SiteColors.HtmlOrange.Text("HTML")
        Text(" to ")
        SiteColors.KobwebBlue.Text("Kobweb")
    })
}

// .cyan-rect {
//   width: 400px;
//   height: 200px;
//   background-color: cyan;
//   border-radius: 10px;
// }

val CyanRectStyle = CssStyle {
    base {
        Modifier
            .width(400.px)
            .height(200.px)
            .backgroundColor(Colors.Cyan)
            .borderRadius(10.px)
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.HtmlToKobwebPage() {

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


//            Div(attrs = {
//                id("example")
//                style {
//                    width(400.px)
//                    height(200.px)
//                    backgroundColor(Color.cyan)
//                    borderRadius(10.px)
//                }
//            })
            Div(
                Modifier
                    .id("example")
                    .width(400.px)
                    .height(200.px)
                    .backgroundColor(Colors.Cyan)
                    .borderRadius(10.px)
                    .toAttrs()
            )
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


            // Style(AppStyleSheet)
            // Div(attrs = {
            //    id("example")
            //    classes(AppStyleSheet.cyanRect)
            // })
            Div(CyanRectStyle.toModifier().id("example").toAttrs())
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
//                classes(AppStyleSheet.cyanRect)
//                ref { element ->
//                    element.style.opacity = "0.5"
//                    onDispose { }
//                }
//            })

            Div(CyanRectStyle.toModifier().id("example").toAttrs {
                ref { element ->
                    element.style.opacity = "0.5"
                    onDispose { }
                }
            })
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            H3(Modifier.color(SiteColors.OffWhite).toAttrs()) {
                FaCodeFork(Modifier.margin(right = 0.3.em))
                Text("Modifiers are a fork!")
            }

            SimpleGrid(numColumns(3), Modifier.gap(Gaps.Normal)) {
                Box(Modifier.step(StepTypes.FadeDown, auto = true)) {
                    CodeBlock(
                        """
                            Modifier
                              .padding(20.px)
                              .fillMaxWidth()


                        """.trimIndent(),
                    )
                }
                Box(Modifier.step(StepTypes.FadeDown, auto = true)) {
                    CodeBlock(
                        """
                            Modifier
                              .fillMaxWidth()
                              .padding(20.px)


                        """.trimIndent(),
                    )
                }
                Box(Modifier.step(StepTypes.FadeDown, auto = true)) {
                    CodeBlock(
                        """
                            Modifier
                              .padding(20.px)
                              .fillMaxWidth()
                              .padding(20.px)
                        """.trimIndent(),
                    )
                }
            }

            H3(Modifier.step().toAttrs()) {
                SiteColors.KobwebBlue.Text("Kobweb")
                Text( " != ")
                SiteColors.Android.Text("Jetpack Compose")
            }
        }
    }
}


