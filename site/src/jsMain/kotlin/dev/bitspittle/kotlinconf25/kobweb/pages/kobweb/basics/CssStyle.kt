@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initCssStylePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("CssStyle"))
}

val HoverRectStyle = CyanRectStyle.extendedBy {
    base {
        Modifier.color(SiteColors.OffBlack)
    }
    hover {
        Modifier.backgroundColor(Colors.Orange)
    }
}


@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.CssStylePage() {
    @Composable
    fun HoverBox(modifier: Modifier = Modifier) {
        Box(HoverRectStyle.toModifier().then(modifier), contentAlignment = Alignment.Center) {
            Text("Hover over me!")
        }
    }

    // CssStyle reminder
    SlideSection {
        val codeWidth = 63.percent
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime, auto = true)) {
            Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """
                    val CyanRectStyle = CssStyle {
                        base {
                            Modifier
                                .width(400.px).height(200.px)
                                .backgroundColor(Colors.Cyan).borderRadius(10.px)
                        }
                    }
                    """.trimIndent(),
                    highlightLines = "0|1,2",
                    preModifier = Modifier.width(codeWidth)
                )
                Div(CyanRectStyle.toAttrs())
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """

                    val CyanRectStyle = CssStyle.base {
                        Modifier
                            .width(400.px).height(200.px)
                            .backgroundColor(Colors.Cyan).borderRadius(10.px)
                    }


                    """.trimIndent(),
                    highlightLines = "2",
                    preModifier = Modifier.width(codeWidth)
                )
                Div(CyanRectStyle.toAttrs())
            }
        }
    }

    // Adding conditional styles / extended by
    SlideSection {
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime, auto = true)) {
            Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """
                    val HoverRectStyle = CssStyle {
                        base {
                            Modifier
                                .width(400.px).height(200.px)
                                .backgroundColor(Colors.Cyan).borderRadius(10.px)
                        }
                    
                        hover { Modifier.backgroundColor(Colors.Orange) }
                    }
                    """.trimIndent(),
                    highlightLines = "0|8",
                    preModifier = Modifier.overflow { y(Overflow.Hidden) }
                )
                HoverBox(Modifier.step())
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """
                    val HoverRectStyle = CssStyle {
                        base {
                            Modifier
                                .width(400.px).height(200.px)
                                .backgroundColor(Colors.Cyan).borderRadius(10.px)
                        }
                    
                        hover { Modifier.backgroundColor(Colors.Orange) }
                    }
                    """.trimIndent(),
                    highlightLines = "3-5,8",
                    preModifier = Modifier.overflow { y(Overflow.Hidden) }
                )
                HoverBox()
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize().gap(Gaps.Large), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """
                    val CyanRectStyle = CssStyle.base { 
                        Modifier
                            .width(400.px).height(200.px)
                            .backgroundColor(Colors.Cyan).borderRadius(10.px)
                    }
                    
                    val HoverRectStyle = CyanRectStyle.extendedBy {
                        hover { Modifier.backgroundColor(Colors.Orange) }
                    }
                    """.trimIndent(),
                    highlightLines = "2-4,8|1,7",
                    preModifier = Modifier.overflow { y(Overflow.Hidden) }
                )
                HoverBox()
            }
        }
    }

    // css2kobweb
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Minor), horizontalAlignment = Alignment.CenterHorizontally) {
            Link("https://opletter.github.io/css2kobweb")
            Video("/assets/videos/css2kobweb.mp4", scale = 1.5f)
        }
    }
}


