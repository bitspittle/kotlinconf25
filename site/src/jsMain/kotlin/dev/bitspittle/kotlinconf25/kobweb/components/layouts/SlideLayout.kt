package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.events.EventListenerManager
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.CSSPercentageNumericValue
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.TransformOrigin
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.bitspittle.kotlinconf25.kobweb.slides
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.w3c.dom.events.KeyboardEvent
import kotlin.math.min

private val PageScale by StyleVariable<Float>()

private val TARGET_WIDTH = 1920
private val TARGET_HEIGHT = 1080

// Slide in from right: 100% -> 0%
// Slide in from left: -100% -> 0%
// Slide out to right: 0% -> 100%
// Slide out to left: 0% -> -100%
private val SlideFromOpacityVar by StyleVariable<Float>()
private val SlideToOpacityVar by StyleVariable<Float>()
private val SlideFromTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()
private val SlideToTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()
val SlideTransitionKeyframes = Keyframes {
    from { Modifier.opacity(SlideFromOpacityVar.value()).translateX(SlideFromTranslatePercentVar.value()) }
    to { Modifier.opacity(SlideToOpacityVar.value()).translateX(SlideToTranslatePercentVar.value()) }
}

val SlideLayoutStyle = CssStyle.base {
    val prettyDarkPurple = Colors.RebeccaPurple.darkened(0.7f)
    val veryDarkPurple = Colors.RebeccaPurple.darkened(0.9f)
    Modifier
        .size(TARGET_WIDTH.px, TARGET_HEIGHT.px)
        .thenIf(
            colorMode.isDark,
            Modifier.backgroundImage(
                linearGradient(
                    prettyDarkPurple,
                    veryDarkPurple,
                    LinearGradient.Direction.ToBottomRight
                )
            )
        )
        .transformOrigin(TransformOrigin.Center)
        .scale(PageScale.value())
        .padding(3.cssRem)
        .overflow(Overflow.Hidden)

}

private enum class SlidingDirection {
    IN_FROM_LEFT,
    IN_FROM_RIGHT,
    OUT_TO_LEFT,
    OUT_TO_RIGHT,
    // Necessary to prevent one frame flicker as new frame comes in
    HIDING;
}

@Layout
@Composable
fun SlideLayout(ctx: PageContext, content: @Composable () -> Unit) {
    LaunchedEffect(ctx.route.path) {
        // See kobweb config in build.gradle.kts which sets up Prism
        js("Prism.highlightAll()")
    }

    fun calculateScale(): Float {
        return min(
            window.innerWidth.toFloat() / TARGET_WIDTH,
            window.innerHeight.toFloat() / TARGET_HEIGHT,
        )
    }

    var scale by remember { mutableStateOf(calculateScale()) }
    var slidingDirection by remember { mutableStateOf<SlidingDirection?>(null) }
    var targetSlide by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        val manager = EventListenerManager(window)

        manager.addEventListener("resize", {
            scale = calculateScale()
        })

        manager.addEventListener("keydown") { event ->
            fun tryNavigateToSlide(delta: Int) {
                if (delta == 0) return
                val origDelta = delta
                var delta = delta
                // If we request to navigate while mid-navigation, use the next target slide as the starting point
                var desiredSlide: String? = targetSlide ?: ctx.route.path.substringAfter("/")
                while (delta > 0 && desiredSlide != null) {
                    desiredSlide = AppGlobals.slides.next(desiredSlide)
                    --delta
                }
                while (delta < 0 && desiredSlide != null) {
                    desiredSlide = AppGlobals.slides.prev(desiredSlide)
                    ++delta
                }
                if (desiredSlide != null) {
                    targetSlide = "/$desiredSlide"
                    if (slidingDirection != null) slidingDirection = SlidingDirection.HIDING
                    window.invokeLater {
                        slidingDirection = if (origDelta < 0) {
                            SlidingDirection.OUT_TO_RIGHT
                        } else {
                            SlidingDirection.OUT_TO_LEFT
                        }
                    }
                }
            }

            var handled = true
            when ((event as KeyboardEvent).key) {
                "ArrowLeft" -> tryNavigateToSlide(-1)
                "ArrowRight" -> tryNavigateToSlide(+1)
                else -> handled = false
            }
            if (handled) {
                event.preventDefault()
                event.stopImmediatePropagation()
            }
        }
        onDispose { manager.clearAllListeners() }
    }

    val colorMode = ColorMode.current
    fun Modifier.slidingAnimation() = animation(
        SlideTransitionKeyframes.toAnimation(
            colorMode,
            0.2.s,
            timingFunction = AnimationTimingFunction.EaseInOut,
        )
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(SlideLayoutStyle.toModifier().setVariable(PageScale, scale)) {
            Box(
                Modifier.fillMaxSize()
                    .thenIf(slidingDirection == SlidingDirection.HIDING) {
                        Modifier.opacity(0f)
                    }
                    .thenIf(slidingDirection == SlidingDirection.OUT_TO_LEFT) {
                        Modifier
                            .setVariable(SlideFromOpacityVar, 1f)
                            .setVariable(SlideToOpacityVar, 0f)
                            .setVariable(SlideFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideToTranslatePercentVar, (-100).percent)
                            .onAnimationEnd {
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingDirection.IN_FROM_RIGHT }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingDirection.OUT_TO_RIGHT) {
                        Modifier
                            .setVariable(SlideFromOpacityVar, 1f)
                            .setVariable(SlideToOpacityVar, 0f)
                            .setVariable(SlideFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideToTranslatePercentVar,  100.percent)
                            .onAnimationEnd {
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingDirection.IN_FROM_LEFT }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingDirection.IN_FROM_LEFT) {
                        Modifier
                            .setVariable(SlideFromOpacityVar, 0f)
                            .setVariable(SlideToOpacityVar, 1f)
                            .setVariable(SlideFromTranslatePercentVar, (-100).percent)
                            .setVariable(SlideToTranslatePercentVar, 0.percent)
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingDirection.IN_FROM_RIGHT) {
                        Modifier
                            .setVariable(SlideFromOpacityVar, 0f)
                            .setVariable(SlideToOpacityVar, 1f)
                            .setVariable(SlideFromTranslatePercentVar, 100.percent)
                            .setVariable(SlideToTranslatePercentVar, 0.percent)
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    },
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}