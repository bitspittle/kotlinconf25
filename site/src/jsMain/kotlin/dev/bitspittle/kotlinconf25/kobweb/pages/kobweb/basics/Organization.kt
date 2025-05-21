@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TransformOrigin
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.DefaultStepSpeed
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Span

@InitRoute
fun initOrganizationPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Project organization"))
}

private const val RESOURCE_FOLDERS_STEP_TYPE = "resource-folders-scaling"

@InitSilk
fun setupScalingStepEffect(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase(".step.${RESOURCE_FOLDERS_STEP_TYPE}") {
            Modifier
                .transition(Transition.of("scale", DefaultStepSpeed))
                .scale(2.5f)
        }

        registerStyleBase(".step.${RESOURCE_FOLDERS_STEP_TYPE}.active") {
            Modifier.scale(1)
        }
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.OrganizationPage() {
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
                    Item("components")
                    Item("pages")
                    Item("AppEntry.kt")
                }
            }
        }
    }

    SlideSection {
        CodeBlock(
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
    }

    SlideSection {
        CodeBlock(
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
        TopStartH3 {
            Folders {
                Bullets {
                    Item("components")
                    Item("pages") {
                        Item("About.kt")
                    }
                }
            }
        }
    }


    SlideSection {
        Box(Modifier.step(StepTypes.OneAtATime, auto = true)) {
            CodeBlock(
                """
                    @Page
                    @Composable
                    @Layout(".components.layouts.PageLayout")
                    fun AboutPage() {
                        /*...*/
                    }
                """.trimIndent(),
                highlightLines = "0|1|3|4"
            )
        }

        CodeBlock(
            """
            @Page
            @Composable
            @Layout(".components.layouts.PageLayout")
            fun AboutPage(ctx: PageContext) {
                /*...*/
            }
            """.trimIndent(),
            highlightLines = "4",
            preModifier = Modifier.step(StepTypes.OneAtATime)
        )

    }

    SlideSection {
        SimpleGrid(numColumns(2), Modifier.gap(Gaps.Large)) {
            Column(Modifier.gap(Gaps.Large)) {
                Folders(Modifier.step(RESOURCE_FOLDERS_STEP_TYPE).transformOrigin(TransformOrigin.TopLeft)) {
                    Bullets {
                        Item("resources/public") {
                            Item("images") {
                                Item("buster.jpg")
                            }
                        }
                    }
                }

                Span(Modifier.step(StepTypes.FadeUp, delay = AnimSpeeds.Fast).toAttrs()) {
                    CodeBlock(
                        """
                            Img(
                                src = "/images/buster.jpg",
                                attrs =
                                    Modifier.fillMaxSize()
                                        .toAttrs()
                            )
                        """.trimIndent(),
                        highlightLines = "1,2",
                    )
                }
            }

            Image("/assets/images/buster.jpg", Modifier.borderRadius(10.px).step(StepTypes.FadeLeft), scale = 0.71f)
        }
    }
}




