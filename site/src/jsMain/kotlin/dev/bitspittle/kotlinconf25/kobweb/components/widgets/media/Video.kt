package dev.bitspittle.kotlinconf25.kobweb.components.widgets.media

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Source
import org.jetbrains.compose.web.dom.Video
import kotlin.math.roundToInt

// ONLY MP4's supported! Easy to add more later, but this is all we need for now.
@Composable
fun Video(src: String, modifier: Modifier = Modifier, autoplay: Boolean = true, loop: Boolean = false, scale: Float? = null) {
    Video(attrs = modifier.toAttrs {
        if (autoplay) {
            attr("autoplay", "")
        }
        if (loop) {
            attr("loop", "")
        }
        if (scale != null)
            ref { videoElement ->
                videoElement.onloadedmetadata = { evt ->
                    videoElement.width = (videoElement.videoWidth * scale).roundToInt()
                    videoElement.height = (videoElement.videoHeight * scale).roundToInt()
                }
                onDispose {}
            }
        }
    ) {
        Source(attrs = {
            this.attr("src", src)
            this.attr("type", "video/mp4")
        })
    }
}

