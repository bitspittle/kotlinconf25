package dev.bitspittle.kotlinconf25.kobweb.components.widgets.shape

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSLengthNumericValue
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

private val StippledRectWidthVar by StyleVariable<CSSLengthNumericValue>()
private val StippledRectHeightVar by StyleVariable<CSSLengthNumericValue>()

val StippledRectStyle = CssStyle.base {
    Modifier
        .width(StippledRectWidthVar.value())
        .height(StippledRectHeightVar.value())
        .backgroundColor(Colors.Transparent)
        .border(
            width = 0.1.cssRem,
            style = LineStyle.Dashed,
            color = SiteColors.OffWhite
        )
        .borderRadius(10.px)
}

@Composable
fun StippledRect(width: CSSLengthNumericValue, height: CSSLengthNumericValue) {
    Box(
        StippledRectStyle.toModifier()
            .setVariable(StippledRectWidthVar, width)
            .setVariable(StippledRectHeightVar, height)
    )
}