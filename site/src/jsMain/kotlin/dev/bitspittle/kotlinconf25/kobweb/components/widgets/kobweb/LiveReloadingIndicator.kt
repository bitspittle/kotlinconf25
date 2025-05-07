package dev.bitspittle.kotlinconf25.kobweb.components.widgets.kobweb

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Div

val SpinAnimation = Keyframes {
    from { Modifier.rotate(0.deg) }
    to { Modifier.rotate(359.deg) }
}

val LiveReloadingIndicatorStyle = CssStyle.base {
    Modifier
        .fontSize(1.4.cssRem)
        .fontWeight(400)
        .backgroundColor(Colors.WhiteSmoke)
        .color(Colors.Black)
        .padding(0.4.cssRem)
        .border(1.px, LineStyle.Solid, Colors.Black)
        .borderRadius(10.px)
}

val LiveReloadingIndicatorSpinningWebStyle = CssStyle.base {
    Modifier.animation(
        SpinAnimation.toAnimation(
            colorMode,
            1.5.s,
            timingFunction = AnimationTimingFunction.Linear,
            iterationCount = AnimationIterationCount.Infinite
        )
    )
        .color(Colors.Gray)
        .display(DisplayStyle.InlineBlock) // Needed for rotation to apply
}

val LiveReloadingIndicatorTextStyle = CssStyle.base {
    Modifier.margin(left = 0.5.cssRem).color(Colors.Black).fontStyle(FontStyle.Italic)
}

@Composable
fun LiveReloadingIndicator() {
    Box(contentAlignment = Alignment.Center) {
        Div(LiveReloadingIndicatorStyle.toAttrs()) {
            SpanText("\uD83D\uDD78\uFE0F", LiveReloadingIndicatorSpinningWebStyle.toModifier())
            SpanText("Building...", LiveReloadingIndicatorTextStyle.toModifier())
        }
    }
}