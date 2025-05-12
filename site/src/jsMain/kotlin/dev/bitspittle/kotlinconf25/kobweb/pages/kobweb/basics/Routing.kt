@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.browser.UrlBar
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.intellij.lang.annotations.Language
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent

@InitRoute
fun initRoutingPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Routing"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.RoutingPage() {

    @Composable
    fun UrlAndCode(
        url: String,
        @Language("kotlin") code: String,
        codeModifier: Modifier = Modifier,
        highlightLines: String? = null,
        extraContent: (@Composable () -> Unit)? = null,
    ) {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            UrlBar(url)
            Box(Modifier.step(StepTypes.FadeIn).fillMaxWidth(70.percent)) {
                CodeBlock(code, preModifier = Modifier.fillMaxWidth(), modifier = codeModifier, highlightLines = highlightLines)
            }

            if (extraContent != null) {
                Box(Modifier.fillMaxWidth().step(), contentAlignment = Alignment.TopCenter) {
                    extraContent()
                }
            }
        }
    }

    // Easiest case -- a simple page
    SlideSection {
        UrlAndCode(
            "https://mysite.com/[about]",
            """
            // com/mysite/pages/About.kt

            package com.mysite.pages

            @Page
            @Composable
            fun AboutPage() {
                /*...*/
            }
            """.trimIndent(),
            highlightLines = "0|1,3,5|7"
        )
    }

    // Index, the magic page with no associated slug
    SlideSection {
        UrlAndCode(
            "https://mysite.com/",
            """
            // com/mysite/pages/Index.kt

            package com.mysite.pages

            @Page
            @Composable
            fun IndexPage() {
                /*...*/
            }
            """.trimIndent(),
            highlightLines = "0|1"
        )
    }

    // Slug with a dash in it
    SlideSection {
        UrlAndCode(
            "https://mysite.com/[kotlinconf-slides]",
            """
                // com/mysite/pages/KotlinconfSlides.kt

                package com.mysite.pages

                @Page
                @Composable
                fun KotlinconfSlidesPage() {
                    /*...*/
                }
            """.trimIndent(),
            highlightLines = "1"
        )
    }

    // Subroutes
    SlideSection {
        UrlAndCode(
            "https://mysite.com/[blog/2025]/kobweb",
            """
                // com/mysite/pages/blog/_2025/Kobweb.kt

                package com.mysite.pages.blog._2025

                @Page
                @Composable
                fun KobwebPage() {
                    /*...*/
                }
            """.trimIndent(),
            highlightLines = "1,3"
        )
    }

    // Dashes in route segments
    SlideSection {
        UrlAndCode(
            "https://mysite.com/[patch-notes]/latest",
            """
                // com/mysite/pages/patchNotes/Latest.kt

                package com.mysite.pages.patchNotes

                @Page
                @Composable
                fun KobwebPage() {
                    /*...*/
                }
            """.trimIndent(),
            highlightLines = "1,3"
        )
    }

    // Route override for slug
    SlideSection {
        UrlAndCode(
            "https://mysite.com/acronyms/[HTML]",
            """
            // com/mysite/pages/acronyms/Html.kt

            package com.mysite.pages

            @Page("HTML")
            @Composable
            fun KobwebPage() {
                /*...*/
            }
            """.trimIndent(),
            highlightLines = "0|1,5"
        )
    }

    // Package mapping for route segment
    SlideSection {
        UrlAndCode(
            "https://mysite.com/releases/[1.0.0]/screenshots",
            """
            // com/mysite/pages/releases/_1_0_0/PackageMapping.kt
            @file:PackageMapping("1.0.0")

            package com.mysite.pages.releases._1_0_0

            import com.varabyte.kobweb.core.PackageMapping
            """.trimIndent(),
            highlightLines = "2,4"
        ) {
            // The "center of mass" for this element looks strange because the children bullets feel like they should
            // be more centered even when the parent container is property centered, so just fudge it a little bit to
            // look subjectively more pleasing
            Folders(Modifier.translateX((-2).cssRem)) {
                Bullets {
                    Item("pages/releases/_1_0_0") {
                        Item("PackageMapping.kt")
                        Item("Screenshots.kt")
                    }
                }
            }
        }
    }

    // Dynamic routes
    SlideSection {
        Column(Modifier.fillMaxSize(85.percent).gap(Gaps.Tiny), horizontalAlignment = Alignment.CenterHorizontally) {
            val urlModifier = Modifier.fillMaxWidth().step(StepTypes.FadeRight, auto = true)
            UrlBar("https://mysite.com/users/[binay]/post/[108]", urlModifier)
            UrlBar("https://mysite.com/users/[cabs]/post/[9]", urlModifier)
            UrlBar("https://mysite.com/users/[ellet]/post/[18]", urlModifier)
            UrlBar("https://mysite.com/users/[opletter]/post/[32]", urlModifier)
            UrlBar("https://mysite.com/users/[stevdza-san]/post/[182]", urlModifier)
            UrlBar("https://mysite.com/users/[thedome]/post/[5]", urlModifier)
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize(85.percent).gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            UrlBar("https://mysite.com/users/[{user}]/post/[{post}]")
            SimpleGrid(numColumns(1), Modifier.step(StepTypes.OneAtATime)) {
                CodeBlock(
                    """
                        @file:PackageMapping("{user}")
                        package com.mysite.pages.users.user
                    """.trimIndent(),
                    highlightLines = "1"
                )
                CodeBlock(
                    """
                        // com/mysite/pages/users/user/posts/Post.kt
                        @Page("{post}")
                    """.trimIndent(),
                    highlightLines = "2"
                )
            }
            SimpleGrid(numColumns(1), Modifier.step(StepTypes.OneAtATime)) {
                CodeBlock(
                    """
                        @file:PackageMapping("{}")
                        package com.mysite.pages.users.user
                    """.trimIndent(),
                )
                CodeBlock(
                    """
                        // com/mysite/pages/users/user/posts/Post.kt
                        @Page("{}")
                    """.trimIndent(),
                )
            }
        }
    }
}


