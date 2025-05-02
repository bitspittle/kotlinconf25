package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.worker.rememberWorker
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.worker.EchoWorker

@Page
@Composable
@Layout(".components.layouts.PageLayout")
fun HomePage() {
    val worker = rememberWorker { EchoWorker { output -> console.log("Echoed: $output") } }
    LaunchedEffect(Unit) {
        worker.postInput("Hello, worker!")
    }

    CodeBlock(
        """
            @Layout
            @Composable
            fun PageLayout(ctx: PageContext, content: @Composable () -> Unit) {
                LaunchedEffect(ctx.route.path) {
                    // See kobweb config in build.gradle.kts which sets up Prism
                    js("Prism.highlightAll()")
                }

                Box(Modifier.fillMaxWidth().minHeight(100.vh), contentAlignment = Alignment.Center) {
                    content()
                }
            }
        """.trimIndent()
    )


}
