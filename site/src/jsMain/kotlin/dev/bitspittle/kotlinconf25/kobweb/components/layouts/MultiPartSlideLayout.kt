package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.events.EventListenerManager
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.CSSPercentageNumericValue
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.ChevronDownIcon
import com.varabyte.kobweb.silk.defer.Deferred
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.w3c.dom.events.KeyboardEvent

@Composable
fun SlideSection(content: @Composable () -> Unit) {
    LocalSlideSections.current.add(content)
}

val LocalSlideSections =
    compositionLocalOf<MutableList<@Composable () -> Unit>> { error("This should only be called under a MultiPartSlideLayout!") }

// Slide in from bottom: 100% -> 0%
// Slide out to bottom: 0% -> 100%
private val SlideVertFromOpacityVar by StyleVariable<Float>()
private val SlideVertToOpacityVar by StyleVariable<Float>()
private val SlideVertFromTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()
private val SlideVertToTranslatePercentVar by StyleVariable<CSSPercentageNumericValue>()

val FadeKeyframes = Keyframes {
    from { Modifier.opacity(SlideVertFromOpacityVar.value()) }
    to { Modifier.opacity(SlideVertToOpacityVar.value()) }
}

val SlideVertKeyframes = Keyframes {
    from { Modifier.opacity(SlideVertFromOpacityVar.value()).translateY(SlideVertFromTranslatePercentVar.value()) }
    to { Modifier.opacity(SlideVertToOpacityVar.value()).translateY(SlideVertToTranslatePercentVar.value()) }
}

val BounceDownKeyframes = Keyframes {
    each(0.percent, 10.percent, 25.percent, 40.percent, 50.percent) {
        Modifier.translateY(0.percent)
    }

    20.percent {
        Modifier.translateY(0.5.cssRem)
    }

    30.percent {
        Modifier.translateY((-0.25).cssRem)
    }
}

private val NavigateArrowOpacityVar by StyleVariable(0f)
val NavigateToNextSectionStyle = CssStyle.base {
    Modifier
        .opacity(NavigateArrowOpacityVar.value())
        .transition(Transition.of("opacity", AnimSpeeds.Quick))
        .color(SiteColors.Accent)
        .animation(
            BounceDownKeyframes.toAnimation(
                AnimSpeeds.Slow,
                timingFunction = TransitionTimingFunction.EaseOut,
                fillMode = AnimationFillMode.Both,
                iterationCount = AnimationIterationCount.Infinite,
            )
        )
}

private enum class SlidingVertDirection {
    FADE_IN,
    FADE_OUT,
    IN_FROM_BOTTOM,
    OUT_TO_BOTTOM,

    // Necessary to prevent one frame flicker as new section comes in
    HIDING;
}


@Suppress("AssignedValueIsNeverRead") // Setting vars has side effects in compose
@Layout(".components.layouts.TitledSlideLayout")
@Composable
fun MultiPartSlideLayout(ctx: PageContext, content: @Composable () -> Unit) {
    var currentSection by remember(ctx.route.path) {
        mutableStateOf(
            window.location.hash.substringAfter("#").toIntOrNull() ?: 0
        )
    }
    val slideSections = remember(ctx.route.path) { mutableListOf<@Composable () -> Unit>() }
    var targetSection by remember { mutableStateOf<Int?>(null) }
    var slidingDirection by remember { mutableStateOf<SlidingVertDirection?>(null) }
    LaunchedEffect(currentSection) {
        window.location.hash = "$currentSection"
    }

    DisposableEffect(Unit) {
        val manager = EventListenerManager(window)

        manager.addEventListener("keydown") { event ->
            fun tryNavigateToSection(delta: Int) {
                if (delta == 0) return

                if (targetSection != null) currentSection = targetSection!!

                val desiredSection = (currentSection + delta).coerceIn(0, slideSections.lastIndex)
                if (desiredSection == currentSection) return

                targetSection = desiredSection
                slidingDirection = null
                window.invokeLater {
                    slidingDirection =
                        if (delta < 0) SlidingVertDirection.OUT_TO_BOTTOM else SlidingVertDirection.FADE_OUT
                }
            }

            var handled = true
            when ((event as KeyboardEvent).key) {
                "ArrowUp" -> tryNavigateToSection(-1)
                "ArrowDown" -> tryNavigateToSection(+1)
                else -> handled = false
            }
            if (handled) {
                event.preventDefault()
                event.stopImmediatePropagation()
            }
        }
        onDispose { manager.clearAllListeners() }
    }

    CompositionLocalProvider(LocalSlideSections provides slideSections) {
        var contentCalled by remember(ctx.route.path) { mutableStateOf(false) }
        if (!contentCalled) {
            content()
            contentCalled = true
        }

        if (slideSections.isNotEmpty()) {
            @Composable
            fun Modifier.fadingAnimation() = animation(
                FadeKeyframes.toAnimation(
                    AnimSpeeds.Quick,
                    timingFunction = TransitionTimingFunction.EaseInOut,
                )
            )

            @Composable
            fun Modifier.slidingAnimation() = animation(
                SlideVertKeyframes.toAnimation(
                    AnimSpeeds.Quick,
                    timingFunction = TransitionTimingFunction.EaseInOut,
                )
            )

            Box(
                Modifier.fillMaxSize()
                    .thenIf(slidingDirection == SlidingVertDirection.HIDING) {
                        Modifier.opacity(0)
                    }
                    .thenIf(slidingDirection == SlidingVertDirection.FADE_OUT) {
                        Modifier
                            .setVariable(SlideVertFromOpacityVar, 1f)
                            .setVariable(SlideVertToOpacityVar, 0f)
                            .onAnimationEnd {
                                currentSection = targetSection!!
                                targetSection = null
                                slidingDirection = SlidingVertDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingVertDirection.IN_FROM_BOTTOM }
                            }
                            .fadingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingVertDirection.FADE_IN) {
                        Modifier
                            .setVariable(SlideVertFromOpacityVar, 0f)
                            .setVariable(SlideVertToOpacityVar, 1f)
                            .onAnimationEnd { slidingDirection = null }
                            .fadingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingVertDirection.OUT_TO_BOTTOM) {
                        Modifier
                            .setVariable(SlideVertFromOpacityVar, 1f)
                            .setVariable(SlideVertToOpacityVar, 0f)
                            .setVariable(SlideVertFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideVertToTranslatePercentVar, 100.percent)
                            .onAnimationEnd {
                                currentSection = targetSection!!
                                targetSection = null
                                slidingDirection = SlidingVertDirection.HIDING
                                window.invokeLater { slidingDirection = SlidingVertDirection.FADE_IN }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingVertDirection.IN_FROM_BOTTOM) {
                        Modifier
                            .setVariable(SlideVertFromOpacityVar, 0f)
                            .setVariable(SlideVertToOpacityVar, 1f)
                            .setVariable(SlideVertFromTranslatePercentVar, 100.percent)
                            .setVariable(SlideVertToTranslatePercentVar, 0.percent)
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    },
                contentAlignment = Alignment.Center
            ) {
                (slideSections.getOrNull(currentSection) ?: slideSections.last()).invoke()
            }
        }

        Deferred {
            Box(
                Modifier.fillMaxWidth().position(Position.Fixed).bottom(0.3.cssRem),
                contentAlignment = Alignment.Center
            ) {
                ChevronDownIcon(
                    NavigateToNextSectionStyle.toModifier()
                        .thenIf(currentSection < slideSections.lastIndex) {
                            Modifier.setVariable(NavigateArrowOpacityVar, 1f)
                        }
                )
            }
        }
    }
}