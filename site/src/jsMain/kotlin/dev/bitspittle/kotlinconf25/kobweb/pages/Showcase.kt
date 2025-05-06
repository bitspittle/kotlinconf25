package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideSection
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.TitledSlideData
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.step
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import org.jetbrains.compose.web.dom.H1

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(TitledSlideData("TODO: Showcase"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ShowcasePage() {
    @Composable
    fun CenteredBox(modifier: Modifier, content: @Composable () -> Unit) {
        Box(Modifier.fillMaxSize().then(modifier), contentAlignment = Alignment.Center) {
            H1 { content() }
        }
    }

    SlideSection {
        Column(Modifier.fillMaxSize().backgroundColor(Colors.Transparent), horizontalAlignment = Alignment.CenterHorizontally) {
            CodeBlock(
                """
                    fun main() {
                        val x = 10
                        println("Hello ${'$'}x")
                        println((x + 10).toString())
                    }
                """.trimIndent(),
                Modifier.fillMaxWidth(),
                highlightLines = "0|1|2|3|4|5|0"
            )

            CodeBlock(
                """
                    fun main() {
                        val names = listOf("Alice", "Bob", "Cindy", "Dan")
                        names.forEach { name ->
                            println("Hello ${'$'}name!")
                        }
                    }
                """.trimIndent(),
                Modifier.fillMaxWidth(),
                highlightLines = "0|1-6|2-5|3,4|1,3,5"
            )

            SpanText("DONE!", Modifier.step())
        }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Green)) {
            Bullets {
                Item("5*", Modifier.step(auto = true))
                Item("6*", Modifier.step(auto = true))
                Item("7*", Modifier.step(auto = true))
                Item("8*", Modifier.step(auto = true))
            }
        }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Blue)) {
            Bullets {
                Item("9", Modifier.step())
                Item("10*", Modifier.step(auto = true))
                Item("11*", Modifier.step(auto = true))
                Item("12", Modifier.step())
            }
        }
    }
    SlideSection {
        CenteredBox(Modifier.backgroundColor(Colors.Orange)) {
            Bullets {
                Item("13", Modifier.step())
                Item("14", Modifier.step())
                Item("15", Modifier.step())
                Item("16*", Modifier.step(auto = true))
            }
        }
    }
}
