package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FlexBasis
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.TransformOrigin
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.aspectRatio
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexBasis
import com.varabyte.kobweb.compose.ui.modifiers.flexGrow
import com.varabyte.kobweb.compose.ui.modifiers.flexShrink
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transformOrigin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import kotlin.math.min

private val PageScale by StyleVariable<Float>()

private val TARGET_WIDTH = 1920.px
private val TARGET_HEIGHT = 1080.px

val PageLayoutStyle = CssStyle.base {
    val prettyDarkPurple = Colors.RebeccaPurple.darkened(0.7f)
    val veryDarkPurple = Colors.RebeccaPurple.darkened(0.9f)
    Modifier
        .size(TARGET_WIDTH, TARGET_HEIGHT)
        .thenIf(colorMode.isDark,
            Modifier.backgroundImage(linearGradient(prettyDarkPurple, veryDarkPurple, LinearGradient.Direction.ToBottomRight))
        )
        .transformOrigin(TransformOrigin.Center)
        .scale(PageScale.value())
        .padding(3.cssRem)
}

@Layout
@Composable
fun PageLayout(ctx: PageContext, content: @Composable () -> Unit) {
    LaunchedEffect(ctx.route.path) {
        // See kobweb config in build.gradle.kts which sets up Prism
        js("Prism.highlightAll()")
    }

    fun calculateScale(): Float {
        return min(
            window.innerWidth.toFloat() / TARGET_WIDTH.value,
            window.innerHeight.toFloat() / TARGET_HEIGHT.value,
        )
    }

    var scale by remember { mutableStateOf(calculateScale()) }
    window.addEventListener("resize", {
        scale = calculateScale()
    })

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(PageLayoutStyle.toModifier().setVariable(PageScale, scale), contentAlignment = Alignment.Center) {
            content()
        }
    }
}