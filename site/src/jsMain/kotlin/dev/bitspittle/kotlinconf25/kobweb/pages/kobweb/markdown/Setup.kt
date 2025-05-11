@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.markdown

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.browser.UrlBar
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.H4

@InitRoute
fun initSetupPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Setup"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun SetupPage() {
    SlideSection {
        CodeBlock(
            """
            plugins {
                alias(libs.plugins.kobwebx.markdown)
            }
            
            kotlin {
                sourceSets {
                    jsMain.dependencies {
                        implementation(libs.kobwebx.markdown)
                    }
                }
            }
        """.trimIndent(),
            highlightLines = "2,8"
        )
    }

    SlideSection {
        H4(Modifier.step(StepTypes.OneAtATime, auto = true).align(Alignment.TopStart).toAttrs()) {
            Folders {
                Bullets {
                    Item("jsMain/resources") {
                        Item("public", Modifier.color(Colors.White.copyf(alpha = 0.5f)))
                        Item("markdown")
                    }
                }
            }
        }
        Column(Modifier.step(StepTypes.OneAtATime).fillMaxSize()) {
            H4(Modifier.toAttrs()) {
                Folders {
                    Bullets {
                        Item("jsMain/resources/markdown") {
                            Item("posts") {
                                Item("Kobweb.md")
                            }
                        }
                    }
                }
            }
            CodeBlock(
                // language=markdown
                """
                # Kobweb
                
                [Kobweb](https://github.com/varabyte/kobweb) is built on
                top of Compose HTML, an official reactive web UI framework
                created by JetBrains.
                """.trimIndent(),
                lang = "markdown",
                preModifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(70.percent).step(StepTypes.FadeUp, auto = true)
            )
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            UrlBar("https://mysite.com/[posts/kobweb]")
            CodeBlock(
                """
                // This file is generated from markdown/posts/Kobweb.md!
                @Page
                @Composable
                fun KobwebPage() {
                    H1 { Text("Kobweb") }
                    /*...*/
                }
                """.trimIndent(),
                preModifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(70.percent).step(StepTypes.FadeUp, auto = true)
            )
        }
    }

}


