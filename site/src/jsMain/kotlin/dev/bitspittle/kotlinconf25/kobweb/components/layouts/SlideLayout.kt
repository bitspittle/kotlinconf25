package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.events.EventListenerManager
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.slides
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.events.KeyboardEvent
import kotlin.math.min
import kotlin.math.roundToInt

private val SlideScaleVar by StyleVariable<Float>()
private val SlidesProgressVar by StyleVariable(0.percent)

private val TARGET_WIDTH = 1920
private val TARGET_HEIGHT = 1080

// Slide in from right: 100% -> 0%
// Slide in from left: -100% -> 0%
// Slide out to right: 0% -> 100%
// Slide out to left: 0% -> -100%
private val SlideHorizFromOpacityVar by StyleVariable<Float>()
private val SlideHorizToOpacityVar by StyleVariable<Float>()
private val SlideHorizFromTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()
private val SlideHorizToTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()
val SlideHorizKeyframes = Keyframes {
    from { Modifier.opacity(SlideHorizFromOpacityVar.value()).translateX(SlideHorizFromTranslatePercentVar.value()) }
    to { Modifier.opacity(SlideHorizToOpacityVar.value()).translateX(SlideHorizToTranslatePercentVar.value()) }
}

val SlideBackgroundStyle = CssStyle.base {
    val prettyDarkPurple = Colors.RebeccaPurple.darkened(0.7f)
    val veryDarkPurple = Colors.RebeccaPurple.darkened(0.9f)
    Modifier
        .fillMaxSize()
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
}


val SlideLayoutStyle = CssStyle.base {
    Modifier
        .size(TARGET_WIDTH.px, TARGET_HEIGHT.px)
        .transformOrigin(TransformOrigin.Center)
        .scale(SlideScaleVar.value())
        .overflow(Overflow.Hidden)
}

val SlidesProgressStyle = CssStyle.base {
    Modifier
        .position(Position.Fixed)
        .bottom(0.px).left(0.px)
        .height(3.px)
        .width(SlidesProgressVar.value())
        .backgroundColor(SiteColors.Accent)
        .transition(Transition.of("width", AnimSpeeds.Quick, TransitionTimingFunction.Ease))
}

private enum class SlidingHorizDirection {
    IN_FROM_LEFT,
    IN_FROM_RIGHT,
    OUT_TO_LEFT,
    OUT_TO_RIGHT,

    // Necessary to prevent one frame flicker as new slide comes in
    HIDING;
}

@Layout
@Composable
fun SlideLayout(ctx: PageContext, content: @Composable () -> Unit) {
    var progressPercent by remember { mutableStateOf(0f) }
    LaunchedEffect(ctx.route.path) {
        // See kobweb config in build.gradle.kts which sets up Prism
        js("Prism.highlightAll()")

        progressPercent =
            AppGlobals.slides.indexOf(ctx.route.path.substringAfter("/")) / (AppGlobals.slides.size - 1).toFloat()
    }

    fun calculateScale(): Float {
        return min(
            window.innerWidth.toFloat() / TARGET_WIDTH,
            window.innerHeight.toFloat() / TARGET_HEIGHT,
        )
    }

    var scale by remember { mutableStateOf(calculateScale()) }
    var slidingDirection by remember { mutableStateOf<SlidingHorizDirection?>(null) }
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
                    if (slidingDirection != null) slidingDirection = SlidingHorizDirection.HIDING
                    window.invokeLater {
                        slidingDirection = if (origDelta < 0) {
                            SlidingHorizDirection.OUT_TO_RIGHT
                        } else {
                            SlidingHorizDirection.OUT_TO_LEFT
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

    @Composable
    fun Modifier.slidingAnimation() = animation(
        SlideHorizKeyframes.toAnimation(
            AnimSpeeds.Quick,
            timingFunction = AnimationTimingFunction.EaseInOut,
        )
    )

    Box(SlideBackgroundStyle.toModifier(), contentAlignment = Alignment.Center) {
        Box(SlideLayoutStyle.toModifier().setVariable(SlideScaleVar, scale)) {
            Box(
                Modifier.fillMaxSize()
                    .padding(3.cssRem)
                    .thenIf(slidingDirection == SlidingHorizDirection.HIDING) {
                        Modifier.opacity(0f)
                    }
                    .thenIf(slidingDirection == SlidingHorizDirection.OUT_TO_LEFT) {
                        Modifier
                            .setVariable(SlideHorizFromOpacityVar, 1f)
                            .setVariable(SlideHorizToOpacityVar, 0f)
                            .setVariable(SlideHorizFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideHorizToTranslatePercentVar, (-100).percent)
                            .onAnimationEnd {
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingHorizDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingHorizDirection.IN_FROM_RIGHT }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingHorizDirection.OUT_TO_RIGHT) {
                        Modifier
                            .setVariable(SlideHorizFromOpacityVar, 1f)
                            .setVariable(SlideHorizToOpacityVar, 0f)
                            .setVariable(SlideHorizFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideHorizToTranslatePercentVar, 100.percent)
                            .onAnimationEnd {
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingHorizDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingHorizDirection.IN_FROM_LEFT }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingHorizDirection.IN_FROM_LEFT) {
                        Modifier
                            .setVariable(SlideHorizFromOpacityVar, 0f)
                            .setVariable(SlideHorizToOpacityVar, 1f)
                            .setVariable(SlideHorizFromTranslatePercentVar, (-100).percent)
                            .setVariable(SlideHorizToTranslatePercentVar, 0.percent)
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingHorizDirection.IN_FROM_RIGHT) {
                        Modifier
                            .setVariable(SlideHorizFromOpacityVar, 0f)
                            .setVariable(SlideHorizToOpacityVar, 1f)
                            .setVariable(SlideHorizFromTranslatePercentVar, 100.percent)
                            .setVariable(SlideHorizToTranslatePercentVar, 0.percent)
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    },
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
    Div(
        SlidesProgressStyle.toModifier()
            .setVariable(SlidesProgressVar, (progressPercent * 100).roundToInt().percent)
            .toAttrs()
    )
}
