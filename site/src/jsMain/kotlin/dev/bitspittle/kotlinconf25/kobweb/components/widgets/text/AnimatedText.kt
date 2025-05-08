package dev.bitspittle.kotlinconf25.kobweb.components.widgets.text

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.util.setInterval
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import kotlinx.browser.window
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// Initial delay default chosen so that text doesn't start animating in while slides / sections are transitioning.
@Composable
fun AnimatedText(
    value: String,
    modifier: Modifier = Modifier,
    keystrokeDelay: Duration = 50.milliseconds,
    initialDelay: Duration = AnimSpeeds.Quick
) {
    var currText by remember { mutableStateOf("") }
    SpanText(currText, modifier, ref = ref {
        window.setInterval(initialDelay, keystrokeDelay) {
            if (currText.length < value.length) {
                currText += value[currText.length]
            } else cancelInterval()
        }
    })
}