package dev.bitspittle.kotlinconf25.kobweb.components.widgets.media

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Source
import org.jetbrains.compose.web.dom.Video
import kotlin.math.roundToInt

@Composable
fun Image(src: String, modifier: Modifier = Modifier, alt: String? = null, scale: Float? = null) {
    Img(src, attrs = modifier.toAttrs {
        alt?.let { attr("alt", alt) }
        if (scale != null) {
            ref { imgElement ->
                imgElement.onload = {
                    imgElement.width = (imgElement.naturalWidth * scale).roundToInt()
                    imgElement.height = (imgElement.naturalHeight * scale).roundToInt()
                }
                onDispose { }
            }
        }
    })
}

