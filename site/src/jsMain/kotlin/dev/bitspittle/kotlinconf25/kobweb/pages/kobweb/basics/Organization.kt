@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initOrganizationPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Project organization"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun OrganizationPage() {
    @Composable
    fun TopStartH3(content: @Composable () -> Unit) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            H3(Modifier.fontWeight(FontWeight.Normal).toAttrs()) {
                content()
            }
        }
    }

    SlideSection {
        TopStartH3 {
            Folders {
                Bullets {
                    Item("components") {
                        Item("pages")
                        Item("AppEntry.kt")
                    }
                }
            }
        }
    }

    SlideSection {
        CodeBlock(
            //language=kotlin
            """
                @App
                @Composable
                fun AppEntry(content: @Composable () -> Unit) {
                    SilkApp {
                        Surface(SmoothColorStyle.toModifier()) {
                            content()
                        }
                    }
                }
            """.trimIndent(),
            highlightLines = "0|1|3,6"
        )
    }

    SlideSection {
        TopStartH3 {
            Folders(Modifier.step(StepTypes.OneAtATime, auto = true)) {
                Bullets {
                    Item("components") {
                        Item("layouts")
                        Item("sections")
                        Item("widgets")
                    }
                }
            }

            Folders(Modifier.step(StepTypes.OneAtATime)) {
                Bullets {
                    Item("components") {
                        Item("layouts") {
                            Item("PageLayout.kt")
                            Item("ArticleLayout.kt")
                        }
                        Item("sections")
                        Item("widgets")
                    }
                }
            }

            Folders(Modifier.step(StepTypes.OneAtATime)) {
                Bullets {
                    Item("components") {
                        Item("layouts")
                        Item("sections") {
                            Item("NavHeader.kt")
                            Item("Footer.kt")
                        }
                        Item("widgets")
                    }
                }
            }

            Folders(Modifier.step(StepTypes.OneAtATime)) {
                Bullets {
                    Item("components") {
                        Item("layouts")
                        Item("sections")
                        Item("widgets") {
                            Item("Button.kt")
                            Item("VisitorCounter.kt")
                        }
                    }
                }
            }
        }

        SlideSection {
            CodeBlock(
                //language=kotlin
                """
                import com.mysite.components.sections.NavHeader
                import com.mysite.components.sections.Footer

                @Layout
                @Composable
                fun PageLayout(content: @Composable () -> Unit) {
                    Column {
                        NavHeader()
                        content()
                        Footer()
                    }
                }
            """.trimIndent(),
                highlightLines = "0|4|6,9|1,2,8,10"
            )
        }

        SlideSection {
            CodeBlock(
                //language=kotlin
                """
                @Page
                @Composable
                @Layout(".components.layouts.PageLayout")
                fun IndexPage() {
                    /*...*/
                }
            """.trimIndent(),
                highlightLines = "0|1|3|5"
            )
        }
    }
}


