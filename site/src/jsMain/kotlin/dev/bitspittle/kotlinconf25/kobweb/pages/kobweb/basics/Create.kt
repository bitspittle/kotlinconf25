@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.px

@InitRoute
fun initCreatePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Creating a project"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.CreatePage() {

    SlideSection {
        Video("/assets/cli/kobweb-create-app.mp4", Modifier.fillMaxSize())
    }

    SlideSection {
        Folders {
            fun Modifier.superQuickStep() = this.step(delay = AnimSpeeds.SuperQuick)
            Bullets(Modifier.fontSize(18.px)) {
                Item(".kobweb/conf.yaml", Modifier.superQuickStep())
                Item("build.gradle.kts", Modifier.superQuickStep())
                Item("gradle/libs.versions.toml", Modifier.superQuickStep())
                Item("site", Modifier.superQuickStep()) {
                    Item("build.gradle.kts", Modifier.superQuickStep())
                    Item("src", Modifier.superQuickStep()) {
                        Item("jsMain", Modifier.superQuickStep()) {
                            Item("kotlin", Modifier.superQuickStep()) {
                                Item("components", Modifier.superQuickStep()) {
                                    Item("layouts", Modifier.superQuickStep()) {
                                        Item("MarkdownLayout.kt", Modifier.superQuickStep())
                                        Item("PageLayout.kt", Modifier.superQuickStep())
                                    }
                                    Item("sections", Modifier.superQuickStep()) {
                                        Item("Footer.kt", Modifier.superQuickStep())
                                        Item("NavHeader.kt", Modifier.superQuickStep())
                                    }
                                    Item("widgets", Modifier.superQuickStep()) {
                                        Item("IconButton.kt", Modifier.superQuickStep())
                                    }
                                }
                                Item("pages", Modifier.superQuickStep()) {
                                    Item("Index.kt", Modifier.superQuickStep())
                                }
                                Item("AppEntry.kt", Modifier.superQuickStep())
                            }
                        }
                        Item("resources", Modifier.superQuickStep()) {
                            Item("markdown/About.md", Modifier.superQuickStep())
                            Item("public/favicon.ico", Modifier.superQuickStep())
                        }
                    }
                }
            }
        }
    }

    SlideSection {
        CodeBlock(
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

    SlideSection {
        CodeBlock(
            //language=yaml
            """
                # .kobweb/conf.yaml
                site:
                  title: "App"
                
                server:
                  port: 8080
                
                  files:
                    dev:
                      contentRoot: "build/processedResources/js/main/public"
                      script: "build/dist/js/developmentExecutable/app.js"
                      api: "build/libs/app.jar"
                    prod:
                      script: "build/dist/js/productionExecutable/app.js"
                      siteRoot: ".kobweb/site"
              """.trimIndent(), highlightLines = "0|6",
            preModifier = Modifier.overflow { y(Overflow.Hidden) }
        )
    }
}


