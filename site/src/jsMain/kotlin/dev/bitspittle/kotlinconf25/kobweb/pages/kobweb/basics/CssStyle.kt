@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
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
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaCodeFork
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.extendedBy
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.pages.html.AppStyleSheet
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

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
fun CssStylePage() {
    @Composable
    fun HoverBox(modifier: Modifier = Modifier) {
        Box(HoverRectStyle.toModifier().then(modifier), contentAlignment = Alignment.Center) {
            Text("Hover over me!")
        }
    }

    // CssStyle reminder
    SlideSection {
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime, auto = true)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                    highlightLines = "0|2"
                )
                Div(CyanRectStyle.toAttrs())
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                CodeBlock(
                    """
                    val CyanRectStyle = CssStyle.base {
                        Modifier
                            .width(400.px).height(200.px)
                            .backgroundColor(Colors.Cyan).borderRadius(10.px)
                    }
                    """.trimIndent(),
                    highlightLines = "1|0"
                )
                Div(CyanRectStyle.toAttrs())
            }
        }
    }

    // Adding conditional styles / extended by
    SlideSection {
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime, auto = true)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                    highlightLines = "0|8"
                )
                HoverBox(Modifier.step())
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                    highlightLines = "3-5,8"
                )
                HoverBox()
            }
        }
        Box(Modifier.fillMaxSize().step(StepTypes.OneAtATime)) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                    highlightLines = "2-4,8|1,7"
                )
                HoverBox()
            }
        }
    }

    // css2kobweb
    SlideSection {
        Column(Modifier.fillMaxSize().gap(0.5.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
            Link("https://opletter.github.io/css2kobweb")
            Video("/assets/videos/css2kobweb.mp4", scale = 1.5f)
        }
    }
}


