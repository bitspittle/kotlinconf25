package dev.bitspittle.kotlinconf25.kobweb.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.worker.rememberWorker
import dev.bitspittle.kotlinconf25.kobweb.worker.EchoWorker
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.PageLayout")
fun HomePage() {
    val worker = rememberWorker { EchoWorker { output -> console.log("Echoed: $output") } }
    LaunchedEffect(Unit) {
        worker.postInput("Hello, worker!")
    }

    Text("THIS PAGE INTENTIONALLY LEFT BLANK")
}
