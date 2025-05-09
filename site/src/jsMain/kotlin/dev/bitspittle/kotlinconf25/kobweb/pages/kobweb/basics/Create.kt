@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.step
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import org.jetbrains.compose.web.css.px

@InitRoute
fun initCreatePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Creating a project"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun CreatePage() {

    SlideSection {
        Video("/assets/cli/kobweb-create-app.mp4", Modifier.fillMaxSize())
    }

    SlideSection {
        Folders {
            Bullets(Modifier.fontSize(18.px)) {
                Item(".kobweb/conf.yaml", Modifier.step(auto = true))
                Item("build.gradle.kts", Modifier.step(auto = true))
                Item("gradle/libs.versions.toml", Modifier.step(auto = true))
                Item("site", Modifier.step(auto = true)) {
                    Item("build.gradle.kts", Modifier.step(auto = true))
                    Item("src", Modifier.step(auto = true)) {
                        Item("jsMain", Modifier.step(auto = true)) {
                            Item("kotlin", Modifier.step(auto = true)) {
                                Item("components", Modifier.step(auto = true)) {
                                    Item("layouts", Modifier.step(auto = true)) {
                                        Item("MarkdownLayout.kt", Modifier.step(auto = true))
                                        Item("PageLayout.kt", Modifier.step(auto = true))
                                    }
                                    Item("sections", Modifier.step(auto = true)) {
                                        Item("Footer.kt", Modifier.step(auto = true))
                                        Item("NavHeader.kt", Modifier.step(auto = true))
                                    }
                                    Item("widgets", Modifier.step(auto = true)) {
                                        Item("IconButton.kt", Modifier.step(auto = true))
                                    }
                                }
                                Item("pages", Modifier.step(auto = true)) {
                                    Item("Index.kt", Modifier.step(auto = true))
                                }
                                Item("AppEntry.kt", Modifier.step(auto = true))
                            }
                        }
                        Item("resources", Modifier.step(auto = true)) {
                            Item("markdown/About.md", Modifier.step(auto = true))
                            Item("public/favicon.ico", Modifier.step(auto = true))
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
            plugins {
                alias(libs.plugins.kotlin.multiplatform)
                alias(libs.plugins.jetbrains.compose)
                alias(libs.plugins.kobweb.application)
                alias(libs.plugins.kobwebx.markdown)
            }

            kobweb {
                app {
                    index {
                        description.set("Powered by Kobweb")
                    }
                }
            }

            kotlin {
                configAsKobwebApplication("app")

                sourceSets {
                    /* ... */
                }
            }
        """.trimIndent(), highlightLines = "0|4|8-14|17"
        )
    }
}


