@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
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
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.shape.StippledRect
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import dev.bitspittle.kotlinconf25.kobweb.util.slides.stepOrder
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
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
            .size(400.px, 200.px)
            .backgroundColor(Colors.Cyan)
            .borderRadius(10.px)
    }
}

// kcKob2

object FlexExampleStyleSheet : StyleSheet() {
    val outer by style {
        width(250.px)
        height(250.px)
        backgroundColor(Colors.White)
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
    }

    val column by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
    }

    val row by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
    }

    val redSquare by style {
        width(100.px)
        height(100.px)
        backgroundColor(Colors.Red)
    }

    val greenSquare by style {
        width(100.px)
        height(100.px)
        backgroundColor(Colors.Green)
    }

    val blueSquare by style {
        width(100.px)
        height(100.px)
        backgroundColor(Colors.Blue)
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.HtmlToKobwebPage() {

    // Super basic HTML
    SlideSection {
        Row(Modifier.fillMaxSize().gap(Gaps.Normal)) {
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

            Box(Modifier.step(StepTypes.FadeLeft)) {
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
                    preModifier = Modifier.step(StepTypes.OneAtATime, auto = true)
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
                    highlightLines = "2-8",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .width(400.px)
                                .height(200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )
                    """.trimIndent(),
                    highlightLines = "2-7",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .width(400.px)
                                .height(200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )
                        """.trimIndent(),
                        highlightLines = "3",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .width(400.px)
                                .height(200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )
                        """.trimIndent(),
                        highlightLines = "4-7",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .width(400.px)
                                .height(200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )
                        """.trimIndent(),
                        highlightLines = "4-5",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .size(400.px, 200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )


                        """.trimIndent(),
                        highlightLines = "4",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Div(attrs = 
                            Modifier
                                .id("example")
                                .size(400.px, 200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                                .toAttrs()
                        )


                        """.trimIndent(),
                        highlightLines = "1,7",
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )

                CodeBlock(
                    """
                        Box( 
                            Modifier
                                .id("example")
                                .size(400.px, 200.px)
                                .backgroundColor(Colors.Cyan)
                                .borderRadius(10.px)
                        )



                        """.trimIndent(),
                    preModifier = Modifier.step(StepTypes.OneAtATime)
                )
            }
        }
    }

    // A little bit of CSS
    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
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
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
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
                    highlightLines = "1,2"
                )

                CodeBlock(
                    """
                     Style(AppStyleSheet)
                     Div(attrs = {
                        id("example")
                        classes(AppStyleSheet.cyanRect)
                     })
                    """.trimIndent(),
                    highlightLines = "1,4"
                )
            }
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
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
                    highlightLines = "2-7"
                )

                Box(Modifier.step(StepTypes.FadeRight)) {
                    CodeBlock(
                        """
                        val CyanRectStyle = CssStyle {
                            base {
                                Modifier
                                    .size(400.px, 200.px)
                                    .backgroundColor(Colors.Cyan)
                                    .borderRadius(10.px)
                            }
                        }
                        """.trimIndent(),
                        highlightLines = "0|1|2,7|3-6"
                    )
                }
            }
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
                CodeBlock(
                    """
                     Style(AppStyleSheet)
                     Div(attrs = {
                        id("example")
                        classes(AppStyleSheet.cyanRect)
                     })
                    """.trimIndent(),
                    highlightLines = "1,4"
                )

                Box(Modifier.step(StepTypes.FadeRight)) {
                    CodeBlock(
                        """
                        Box(
                            CyanRectStyle.toModifier()
                                .id("example")
                        )


                        """.trimIndent(),
                        highlightLines = "0|2|3"
                    )
                }
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

            Box(Modifier.step(StepTypes.FadeRight)) {
                SimpleGrid(numColumns(2), Modifier.gap(Gaps.Normal)) {
                    CodeBlock(
                        """
                        Div(attrs = {
                            classes(AppStyleSheet.cyanRect)
                            ref { element ->
                                element.style.opacity = "0.5"
                                onDispose {}
                            }
                        })
                        """.trimIndent(),
                        highlightLines = "3-6"
                    )
                    Box(Modifier.step(StepTypes.FadeLeft)) {
                        CodeBlock(
                            """
                            Box(                        
                                CyanRectStyle.toModifier(),
                                ref = ref { element ->
                                    element.style.opacity = "0.5"
                                },
                            )


                            """.trimIndent(),
                            highlightLines = "0|3-5"
                        )
                    }
                }
            }
        }
    }

    // Box, Row, and Column
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Minor), horizontalAlignment = Alignment.CenterHorizontally) {
            SimpleGrid(numColumns(1), Modifier.gap(Gaps.Minor).height(62.percent)) {
                CodeBlock(
                    // language=html
                    """
                        <div class="outer">
                            <div class="column">
                                <div class="row">
                                    <div class="red-square" />
                                    <div class="green-square" />
                                </div>
                                <div class="blue-square" />
                            </div>
                        </div> 
                        """.trimIndent(),
                    lang = "html",
                    highlightLines = "0|2|3|4-5,7",
                    preModifier = Modifier.stepOrder(order = 2)
                )
            }

            Style(FlexExampleStyleSheet)
            Row(Modifier.gap(Gaps.Minor).step(order = 1)) {
                Div(attrs = { classes(FlexExampleStyleSheet.outer) }) {
                    Div(attrs = { classes(FlexExampleStyleSheet.column) }) {
                        Div(attrs = { classes(FlexExampleStyleSheet.row) }) {
                            Div(attrs = { classes(FlexExampleStyleSheet.redSquare) })
                            Div(attrs = { classes(FlexExampleStyleSheet.greenSquare) })
                        }
                        Div(attrs = { classes(FlexExampleStyleSheet.blueSquare) })
                    }
                }
            }
        }
    }

    SlideSection {
        Column(
            Modifier.fillMaxSize().gap(Gaps.Minor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleGrid(numColumns(2), Modifier.gap(Gaps.Minor).height(62.percent)) {
                CodeBlock(
                    // language=html
                    """
                        <div class="outer">
                            <div class="column">
                                <div class="row">
                                    <div class="red-square" />
                                    <div class="green-square" />
                                </div>
                                <div class="blue-square" />
                            </div>
                        </div> 
                        """.trimIndent(),
                    lang = "html",
                )

                CodeBlock(
                    // language=css
                    """
                        .outer {
                            width: 250px;
                            height: 250px;
                            background-color: gray;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                        }
                        .column {
                            display: flex;
                            flex-direction: column;
                        }
                        .row {
                            display: flex;
                            flex-direction: row;
                        }
                        .red-square {
                            width: 100px;
                            height: 100px;
                            background-color: red;
                        }
                        .green-square {
                            width: 100px;
                            height: 100px;
                            background-color: green;
                        }
                        .blue-square {
                            width: 100px;
                            height: 100px;
                            background-color: blue;
                        }
                        """.trimIndent(),
                    lang = "css",
                    highlightLines = "0|1-8|5-7|9-12|13-16",
                )
            }

            Style(FlexExampleStyleSheet)
            Row(Modifier.gap(Gaps.Minor)) {
                Div(attrs = { classes(FlexExampleStyleSheet.outer) }) {
                    Div(attrs = { classes(FlexExampleStyleSheet.column) }) {
                        Div(attrs = { classes(FlexExampleStyleSheet.row) }) {
                            Div(attrs = { classes(FlexExampleStyleSheet.redSquare) })
                            Div(attrs = { classes(FlexExampleStyleSheet.greenSquare) })
                        }
                        Div(attrs = { classes(FlexExampleStyleSheet.blueSquare) })
                    }
                }
            }
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Minor), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                """
                val squareModifier = Modifier.size(100.px)
                Box(Modifier.size(250.px).backgroundColor(Colors.White), contentAlignment = Alignment.Center) {
                    Column {
                        Row {
                            Box(squareModifier.backgroundColor(Colors.Red))
                            Box(squareModifier.backgroundColor(Colors.Green))
                        }
                        Box(squareModifier.backgroundColor(Colors.Blue))
                    }
                }
                """.trimIndent(),
                highlightLines = "0|2|3,4|5,6,8"
            )

            val squareModifier = Modifier.size(100.px)
            Box(Modifier.size(250.px).backgroundColor(Colors.White), contentAlignment = Alignment.Center) {
                Column {
                    Row {
                        Box(squareModifier.backgroundColor(Colors.Red))
                        Box(squareModifier.backgroundColor(Colors.Green))
                    }
                    Box(squareModifier.backgroundColor(Colors.Blue))
                }
            }
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
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


