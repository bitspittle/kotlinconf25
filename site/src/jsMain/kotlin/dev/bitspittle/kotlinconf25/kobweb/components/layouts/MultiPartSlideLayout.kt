package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.events.EventListenerManager
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.SVGFillRule
import com.varabyte.kobweb.compose.dom.svg.ViewBox
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.BoxScope
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.IconRenderStyle
import com.varabyte.kobweb.silk.components.icons.createIcon
import com.varabyte.kobweb.silk.defer.Deferred
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.bindings.prismjs.Prism
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.activateAllSteps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.deactivateAllSteps
import dev.bitspittle.kotlinconf25.kobweb.util.toCssUnit
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H2
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent

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

private val MultiPartTitleTranslateYVar by StyleVariable<CSSLengthOrPercentageNumericValue>(0.px)
val MultiPartTitleStyle = CssStyle.base {
    Modifier
        .textAlign(TextAlign.Center)
        .translateY(MultiPartTitleTranslateYVar.value())
        .transition(Transition.of("translate", AnimSpeeds.Fast.toCssUnit(), timingFunction = TransitionTimingFunction.EaseOut))
}

private val NavigateArrowOpacityVar by StyleVariable(0f)
val NavigateToNextSectionStyle = CssStyle.base {
    Modifier
        .opacity(NavigateArrowOpacityVar.value())
        .transition(Transition.of("opacity", AnimSpeeds.Quick.toCssUnit()))
        .color(SiteColors.Accent)
}

private enum class SlidingVertDirection {
    FADE_IN,
    FADE_OUT,
    IN_FROM_BOTTOM,
    OUT_TO_BOTTOM,

    // Necessary to prevent one frame flicker as new section comes in
    HIDING;
}

@Composable
private fun NavDownIcon(modifier: Modifier = Modifier) {
    // From https://icons.getbootstrap.com/icons/chevron-down/
    // It's a bit thinner than the default SVG provided by Kobweb
    createIcon(viewBox = ViewBox.sized(16), width = 0.7.em, renderStyle = IconRenderStyle.Stroke(1), attrs = modifier.toAttrs()) {
        Path {
            fillRule(SVGFillRule.EvenOdd)
            d {
                moveTo(1.646, 4.646)
                ellipticalArc(0.5, 0.5, 0, 0, 1, 0.708, 0, isRelative = true)
                lineTo(8, 10.293)
                lineTo(5.646, -5.647, isRelative = true)
                ellipticalArc(0.5, 0.5, 0, 0, 1, 0.708, 0.708, isRelative = true)
                lineTo(-6, 6, true)
                ellipticalArc(0.5, 0.5, 0, 0, 1, -0.708, 0, isRelative = true)
                lineTo(-6, -6, true)
                ellipticalArc(0.5, 0.5, 0, 0, 1, 0, -0.708, isRelative = true)
                closePath()
            }
        }
    }
}

// Once we visit a multi-part section and leave it, we always want to remember where we left from. (So if we navigate
// away from it by accident and go back, we'll be back on the same page).
// We declare this outside the composable to ensure that it doesn't get lost even if you naviagte to another slide that
// doesn't use this layout, the data won't get lost.
private val currentSections = mutableStateMapOf<String, Int>()

class MultiPartSlideLayoutScope(wrapped: SlideLayoutScope) : SlideLayoutScope(wrapped.slideUtils) {
    internal val sections = mutableListOf<@Composable BoxScope.() -> Unit>()

    @Composable
    fun SlideSection(content: @Composable BoxScope.() -> Unit) {
        sections.add(content)
    }
}

@Suppress("AssignedValueIsNeverRead") // Setting vars has side effects in compose
@Layout(".components.layouts.SlideLayout")
@Composable
fun SlideLayoutScope.MultiPartSlideLayout(ctx: PageContext, content: @Composable MultiPartSlideLayoutScope.() -> Unit) {
    val multiPartSlideLayoutScope = remember(ctx.route.path) { MultiPartSlideLayoutScope(this) }
    val slideSections = multiPartSlideLayoutScope.sections

    fun getCurrentSection() = currentSections[ctx.route.path] ?: -1
    fun setCurrentSection(section: Int) { currentSections[ctx.route.path] = section }

    DisposableEffect(ctx.route.path) {
        if (!currentSections.containsKey(ctx.route.path)) {
            window.location.hash.substringAfter("#").toIntOrNull()?.takeIf { it >= 0 }?.let { currentSection ->
                setCurrentSection(currentSection)
            }
        }
        onDispose { }
    }

    var targetSection by remember { mutableStateOf<Int?>(null) }
    var slidingDirection by remember { mutableStateOf<SlidingVertDirection?>(null) }
    var navigatingOut by remember(ctx.route.path) {
        mutableStateOf(false)
            .also { state ->
                ctx.data.getValue<SlideEvents>().onNavigating += { args ->
                    state.value = true
                }
            }
    }
    var containerElement by remember(ctx.route.path) { mutableStateOf<HTMLElement?>(null) }

    LaunchedEffect(ctx.route.path) {
        ctx.data.getValue<SlideEvents>().onEntered += { args ->
            if (!args.forward) {
                // If we are coming in from the right, that means we passed the last section and are now coming back to
                // it.
                setCurrentSection(slideSections.lastIndex)
            }
        }

        ctx.data.getValue<SlideEvents>().onExiting += { args ->
            if (!args.forward) {
                // If we leave off to the left, then reset the current section so next time we come in we're back
                // at the beginning again.
                currentSections.remove(ctx.route.path)
            }
        }
    }

    LaunchedEffect(getCurrentSection()) {
        window.location.hash = getCurrentSection().takeIf { it >= 0 }?.let { "$it" }.orEmpty()
    }

    LaunchedEffect(containerElement, getCurrentSection()) {
        if (getCurrentSection() < 0) return@LaunchedEffect
        val containerElement = containerElement ?: return@LaunchedEffect
        Prism.highlightAllUnder(containerElement)
    }

    fun tryNavigateToSection(delta: Int): Boolean {
        if (delta == 0) return false

        // If target section is not null, it means the user keeps pressing the arrow key mid-animation
        if (targetSection != null) setCurrentSection(targetSection!!)

        val desiredSection = (getCurrentSection() + delta).coerceIn(-1, slideSections.lastIndex)
        if (desiredSection == getCurrentSection()) return false

        targetSection = desiredSection
        slidingDirection = null
        window.invokeLater {
            slidingDirection =
                if (delta < 0) SlidingVertDirection.OUT_TO_BOTTOM else SlidingVertDirection.FADE_OUT
        }

        return true
    }

    DisposableEffect(containerElement) {
        val containerElement = containerElement ?: return@DisposableEffect onDispose {}
        val manager = EventListenerManager(containerElement)
        manager.addEventListener("keydown") { event ->
            var handled = true
            when ((event as KeyboardEvent).key) {
                "ArrowUp" -> {
                    slideUtils.cancelRunningSteps()
                    if (!containerElement.deactivateAllSteps() || event.shiftKey) {
                        tryNavigateToSection(-1)
                    }
                }

                "ArrowDown" -> {
                    slideUtils.cancelRunningSteps()
                    if (!containerElement.activateAllSteps() || event.shiftKey) {
                        tryNavigateToSection(+1)
                    }
                }

                "Home" -> {
                    if (!event.shiftKey) {
                        slideUtils.cancelRunningSteps()
                        tryNavigateToSection(-getCurrentSection() - 1)
                    } else {
                        handled = false
                    }
                }

                "End" -> {
                    if (!event.shiftKey) {
                        slideUtils.cancelRunningSteps()
                        if (!tryNavigateToSection(slideSections.lastIndex - getCurrentSection())) {
                            // If we failed, we're already on the bottom section (but with steps not yet triggered).
                            // Go ahead and trigger them all.
                            slideUtils.activateAllSteps()
                        }
                    } else {
                        handled = false
                    }
                }

                else -> handled = false
            }
            if (handled) {
                event.preventDefault()
                event.stopImmediatePropagation()
            }
        }
        onDispose { manager.clearAllListeners() }
    }

    LaunchedEffect(ctx.route.path) {
        ctx.data.getValue<SlideEvents>().onStepRequested += { args ->
            tryNavigateToSection(if (args.forward) +1 else -1)
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        H2(
            MultiPartTitleStyle.toModifier()
            .thenIf(getCurrentSection() == -1) {
                Modifier.setVariable(MultiPartTitleTranslateYVar, 350.px)
            }
            .toAttrs()
        ) {
            ctx.data.getValue<SlideTitle>().renderTitle.invoke()
        }

        var contentCalled by remember(ctx.route.path) { mutableStateOf(false) }
        if (!contentCalled) {
            content(multiPartSlideLayoutScope)
            contentCalled = true
        }

        if (slideSections.isNotEmpty()) {
            @Composable
            fun Modifier.fadingAnimation() = animation(
                FadeKeyframes.toAnimation(
                    AnimSpeeds.Quick.toCssUnit(),
                    timingFunction = TransitionTimingFunction.EaseInOut,
                )
            )

            @Composable
            fun Modifier.slidingAnimation() = animation(
                SlideVertKeyframes.toAnimation(
                    AnimSpeeds.Quick.toCssUnit(),
                    timingFunction = TransitionTimingFunction.EaseInOut,
                )
            )

            Box(
                Modifier.fillMaxSize()
                    .overflow(Overflow.Hidden)
                    .padding(top = 1.cssRem) // Space between title and content
                    .thenIf(slidingDirection == SlidingVertDirection.HIDING) {
                        Modifier.opacity(0)
                    }
                    .thenIf(slidingDirection == SlidingVertDirection.FADE_OUT) {
                        Modifier
                            .setVariable(SlideVertFromOpacityVar, 1f)
                            .setVariable(SlideVertToOpacityVar, 0f)
                            .onAnimationEnd {
                                setCurrentSection(targetSection!!)
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
                            .onAnimationStart {
                                slideUtils.refreshSteps()
                                // We're returning to a previously visited slide, so ensure all steps are active
                                containerElement!!.activateAllSteps()
                            }
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
                                setCurrentSection(targetSection!!)
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
                            .onAnimationStart { slideUtils.refreshSteps() }
                            .onAnimationEnd { slidingDirection = null }
                            .slidingAnimation()
                    },
                contentAlignment = Alignment.Center,
                ref = ref {
                    it.tabIndex = 0
                    it.focus()
                    containerElement = it
                },
            ) {
                if (getCurrentSection() >= 0) {
                    // Should never be null but might happen in development if you remove a section and then a reload
                    // happens, or if someone manually enters an invalid hash fragment
                    (slideSections.getOrNull(getCurrentSection()) ?: slideSections.last()).invoke(this)
                }
            }
        }

        Deferred {
            Box(
                Modifier.fillMaxWidth().position(Position.Fixed).bottom(0.25.cssRem),
                contentAlignment = Alignment.Center
            ) {
                NavDownIcon(
                    NavigateToNextSectionStyle.toModifier()
                        .thenIf(!navigatingOut && getCurrentSection() < slideSections.lastIndex) {
                            Modifier.setVariable(NavigateArrowOpacityVar, 1f)
                        }
                )
            }
        }
    }
}