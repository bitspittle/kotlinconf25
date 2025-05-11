@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.fullstack

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.MinWidth
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
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
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initApiRoutesPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("API routes"))
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun ApiRoutesPage() {

    // Basic API route
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            H3(Modifier.fontWeight(FontWeight.Normal).toAttrs()) {
                Folders {
                    Bullets {
                        Item("jvmMain") {
                            Item("api")
                        }
                    }
                }
            }
            Box(Modifier.step(StepTypes.FadeUp)) {
                CodeBlock(
                    """
                    // jvmMain/com/mysite/api/hello.kt
                    @Api
                    suspend fun hello(ctx: ApiContext) {
                        ctx.res.setBodyText("hello world")
                    }
                    """.trimIndent(),
                    highlightLines = "0|1|2|3"
                )
            }
        }
    }

    // Calling the API route from the frontend
    SlideSection {
        Column(Modifier.gap(Gaps.Tiny), horizontalAlignment = Alignment.CenterHorizontally) {
            fun Modifier.commonWidth() = fillMaxWidth()
            Box(Modifier.commonWidth()) {
                CodeBlock(
                    """
                    @Api
                    suspend fun hello(ctx: ApiContext) { /*...*/ }
                    """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth()
                )
            }

            Box(Modifier.step(StepTypes.FadeUp).commonWidth()) {
                CodeBlock(
                    """
                    @Page
                    @Composable
                    fun ExamplePage() {
                        var fetchedText by remember { mutableStateOf("") }
                        LaunchedEffect(Unit) {
                            fetchedText = 
                                window.api.get("hello")
                                    .decodeToString()
                        }
                    }
                    """.trimIndent(),
                    highlightLines = "0|7",
                    preModifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


