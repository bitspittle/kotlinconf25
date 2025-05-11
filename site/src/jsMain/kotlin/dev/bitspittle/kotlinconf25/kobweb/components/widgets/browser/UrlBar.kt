package dev.bitspittle.kotlinconf25.kobweb.components.widgets.browser

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowLeft
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRight
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRotateRight
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.extendedByBase
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span as JbSpan

val UrlBarContainerStyle = CssStyle.base {
    Modifier
        .borderRadius(5.px)
        .backgroundColor(SiteColors.KobwebBlue)
        .gap(Gaps.Normal)
        .padding(leftRight = Gaps.Normal, topBottom = Gaps.Minor)
}

val UrlAreaStyle = CssStyle.base {
    Modifier
        .borderRadius(10.px)
        .backgroundColor(Colors.DarkSlateGrey)
        .fontFamily("monospace")
        .fontSize(0.9.cssRem)
        .padding(leftRight = Gaps.Minor, topBottom = Gaps.Miniscule)
        .flexGrow(1)
}

val UrlIconStyle = CssStyle.base {
    Modifier
        .color(Colors.White.copyf(alpha = 0.8f))
        .fontSize(1.1.cssRem)
}

val DisabledUrlIconStyle = UrlIconStyle.extendedByBase {
    Modifier.color(Colors.LightGray.copyf(alpha = 0.6f))
}

// Optional: You can highlight parts of the URL with square brackets
// www.example.com/[hello]/
@Composable
fun UrlBar(url: String, modifier: Modifier = Modifier, id: String? = null) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Row(
            UrlBarContainerStyle.toModifier().thenIf(id != null, Modifier.attrsModifier { attr("data-id", id!!) })
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FaArrowLeft(DisabledUrlIconStyle.toModifier())
            FaArrowRight(DisabledUrlIconStyle.toModifier())
            FaArrowRotateRight(UrlIconStyle.toModifier())
            Box(UrlAreaStyle.toModifier()) {
                val urlWithOrigin = if (url.contains("://")) url else "https://$url"
                val finalUrl = if (!urlWithOrigin.contains('[')) urlWithOrigin else
                    urlWithOrigin
                        .replace("[", "<span style=\"color:cyan\">")
                        .replace("]", "</span>")


                JbSpan(attrs = {
                    ref { element -> element.innerHTML = finalUrl; onDispose {  } }
                })
            }
        }
    }
}