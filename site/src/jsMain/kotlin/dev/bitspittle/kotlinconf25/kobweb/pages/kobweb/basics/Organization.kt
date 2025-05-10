@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideEvents
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initOrganizationPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Project organization"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun OrganizationPage(ctx: PageContext) {
    @Composable
    fun TopStartH3(content: @Composable () -> Unit) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            H3(Modifier.fontWeight(FontWeight.Normal).toAttrs()) {
                content()
            }
        }
    }

    val slideEvents = ctx.data.getValue<SlideEvents>()



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
        var phase by remember { mutableStateOf(0) }

        DisposableEffect(Unit) {
            val stepCallback = slideEvents.onStepRequested.add { evt ->
                if (evt.forward && phase < 3) {
                    phase++
                    true
                } else if (!evt.forward && phase > 0) {
                    phase--
                    true
                } else {
                    false
                }
            }
            val enteredCallback = slideEvents.onEntered.add { evt ->
                println("Entered")
                phase = if (evt.forward) 0 else 3
            }
            onDispose {
                slideEvents.onStepRequested -= stepCallback
                slideEvents.onEntered -= enteredCallback
            }
        }

        TopStartH3 {
            when (phase) {
                0 -> {
                    Folders {
                        Bullets {
                            Item("components") {
                                Item("layouts")
                                Item("sections")
                                Item("widgets")
                            }
                        }
                    }
                }

                1 -> {
                    Folders {
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
                }

                2 -> {
                    Folders {
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
                }

                3 -> {
                    Folders {
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
            }
        }

        SlideSection {
            CodeBlock(
                //language=kotlin
                """
                import com.mysite.components.sections.NavHeader
                import com.mysite.components.sections.Footer

                @Layout
                fun PageLayout(content: @Composable () -> Unit) {
                    Column {
                        NavHeader()
                        content()
                        Footer()
                    }
                }
            """.trimIndent(),
                highlightLines = "0|5,8|1,2,7,9"
            )
        }

        SlideSection {
            CodeBlock(
                //language=kotlin
                """
                import com.mysite.components.layouts.PageLayout

                @Page
                @Composable
                @Layout(".components.layouts.PageLayout")
                fun IndexPage() {
                    /*...*/
                }
            """.trimIndent(),
                highlightLines = "0|5|0"
            )
        }
    }
}


