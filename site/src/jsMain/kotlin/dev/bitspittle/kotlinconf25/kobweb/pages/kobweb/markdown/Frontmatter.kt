package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.markdown

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step

@InitRoute
fun initFrontMatterPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Front Matter"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun FrontMatterPage() {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().gap(Gaps.Normal).step(StepTypes.OneAtATime, auto = true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeBlock(
                //language=yaml
                """
                ---
                layout: .components.layouts.ArticleLayout
                author: BitSpittle
                tags:
                  - Web Dev
                  - Kotlin
                ---
                """.trimIndent(),
                lang = "yaml",
                highlightLines = "0|2|3-6"
            )

            Box(Modifier.step(StepTypes.FadeUp)) {
                CodeBlock(
                    """
                    @Layout
                    @Composable
                    fun ArticleLayout(ctx: PageContext, content: @Composable () -> Unit) {
                        val fm = ctx.markdown!!.frontMatter
                        val author = fm.getValue("author").single()
                        val tags = markdown.frontMatter.getValue("tags")
                    }
                    """.trimIndent(),
                    lang = "yaml",
                    highlightLines = "0|4"
                )
            }
        }

        Column(
            Modifier.fillMaxSize().gap(Gaps.Normal).step(StepTypes.OneAtATime),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeBlock(
                //language=yaml
                """
                ---
                layout: .components.layouts.ArticleLayout
                author: BitSpittle
                tags:
                  - Web Dev
                  - Kotlin
                ---
                """.trimIndent(),
                lang = "yaml",
                highlightLines = "3"
            )

            CodeBlock(
                """
                @Layout
                @Composable
                fun ArticleLayout(ctx: PageContext, content: @Composable () -> Unit) {
                    val fm = ctx.markdown!!.frontMatter
                    val author = fm.getValue("author").single()
                    val tags = markdown.frontMatter.getValue("tags")
                }
                """.trimIndent(),
                lang = "yaml",
                highlightLines = "5"
            )
        }

        Column(
            Modifier.fillMaxSize().gap(Gaps.Normal).step(StepTypes.OneAtATime),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeBlock(
                //language=yaml
                """
                ---
                layout: .components.layouts.ArticleLayout
                author: BitSpittle
                tags:
                  - Web Dev
                  - Kotlin
                ---
                """.trimIndent(),
                lang = "yaml",
                highlightLines = "4-6"
            )

            CodeBlock(
                """
                @Layout
                @Composable
                fun ArticleLayout(ctx: PageContext, content: @Composable () -> Unit) {
                    val fm = ctx.markdown!!.frontMatter
                    val author = fm.getValue("author").single()
                    val tags = markdown.frontMatter.getValue("tags")
                }
                """.trimIndent(),
                lang = "yaml",
                highlightLines = "6"
            )
        }
    }
}
