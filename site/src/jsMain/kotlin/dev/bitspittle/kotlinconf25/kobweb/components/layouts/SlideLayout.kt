package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.events.EventListenerManager
import com.varabyte.kobweb.browser.util.CancellableActionHandle
import com.varabyte.kobweb.browser.util.invokeLater
import com.varabyte.kobweb.browser.util.setTimeout
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.activeSubstepIndex
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.substepCount
import dev.bitspittle.kotlinconf25.kobweb.slides
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.toCssUnit
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement
import org.w3c.dom.MutationObserver
import org.w3c.dom.MutationObserverInit
import org.w3c.dom.asList
import org.w3c.dom.events.KeyboardEvent
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private val SlideScaleVar by StyleVariable<Float>()
private val SlidesProgressVar by StyleVariable(0.percent)

private const val TARGET_WIDTH = 1920
private const val TARGET_HEIGHT = 1080

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
        .transition(Transition.of("width", AnimSpeeds.Quick.toCssUnit(), TransitionTimingFunction.Ease))
}

private enum class SlidingHorizDirection {
    IN_FROM_LEFT,
    IN_FROM_RIGHT,
    OUT_TO_LEFT,
    OUT_TO_RIGHT,

    // Necessary to prevent one frame flicker as a new slide comes in
    HIDING;
}

interface EventArgs

abstract class Event<A : EventArgs, R> {
    abstract fun add(callback: (A) -> R)
    operator fun plusAssign(callback: (A) -> R) = add(callback)
}

class EventImpl<A : EventArgs, R> : Event<A, R>() {
    private val callbacks = mutableListOf<(A) -> R>()
    override fun add(callback: (A) -> R) { callbacks.add(callback) }

    fun asSequence(param: A): Sequence<R> = callbacks.asSequence().map { it(param) }

    operator fun invoke(param: A) {
        // Convert to list which consumes the sequence as a side effect
        asSequence(param).toList()
    }
}

interface SlideUtils {
    fun cancelRunningSteps()

    companion object {
        @get:Composable
        val instance get() = SlideUtilsLocal.current
    }
}

val SlideUtilsLocal = compositionLocalOf<SlideUtils> { error("SlideUtils can only be accessed within a SlideLayout") }

@Suppress("unused")
object EmptyEventArgs : EventArgs
class DirectionArgs(val forward: Boolean) : EventArgs

class SlideEvents {
    val onNavigating: Event<DirectionArgs, Unit> = EventImpl()
    val onNavigated: Event<DirectionArgs, Unit> = EventImpl()
    val onStepRequested: Event<DirectionArgs, Boolean> = EventImpl()
}

@InitRoute
fun initSlideLayout(ctx: InitRouteContext) {
    ctx.data.add(SlideEvents())
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
    var containerElement by remember { mutableStateOf<HTMLElement?>(null) }
    val stepElements = remember { mutableListOf<HTMLElement>() }

    // Remember this handle so that later when we capture it in a closer, it's definitely the same handle
    var cancelHandle by remember { mutableStateOf<CancellableActionHandle?>(null) }
    fun enqueueWithDelay(delay: Duration, action: () -> Unit) {
        cancelHandle?.cancel()
        cancelHandle = window.setTimeout(delay) {
            if (cancelHandle == null) return@setTimeout
            cancelHandle = null
            action()
        }
    }

    fun HTMLElement.enqueueWithDelayIfAuto(action: () -> Unit) {
        if (classList.contains("auto")) {
            val delay =
                getAttribute("data-step-delay")?.toIntOrNull()?.milliseconds ?: AnimSpeeds.Quick
            enqueueWithDelay(delay, action)
        }
    }

    // If force is true, automatically activate / deactivate everything all at once. Useful if wanting to show all
    // steps instantly while debugging, or if you accidentally go too far and want to go back
    fun tryStep(forward: Boolean): Boolean {
        if (stepElements.isEmpty()) return false

        return if (forward) {
            var stepActivated = false
            for (stepElement in stepElements) {
                if (!stepElement.classList.contains("active")) {
                    if (stepActivated) {
                        stepElement.enqueueWithDelayIfAuto { tryStep(forward) }
                        break
                    } else {
                        stepElement.classList.add("active")
                        stepActivated = true
                    }
                }

                // Handle multi-part steps if this is one (basically, even if it is active, it keeps consuming steps)
                if (stepElement.substepCount > 1) {
                    if (stepElement.activeSubstepIndex < stepElement.substepCount - 1) {
                        stepElement.activeSubstepIndex++
                        stepActivated = true
                        break
                    }
                }
            }
            stepActivated
        } else {
            var stepDeactivated = false
            // Remove all grouped auto steps in one fell swoop
            for (stepElement in stepElements.asReversed()) {
                if (stepElement.classList.contains("active")) {
                    // Handle multi-part steps if this is one
                    if (stepElement.substepCount > 1) {
                        if (stepElement.activeSubstepIndex > 0) {
                            stepElement.activeSubstepIndex--
                            stepDeactivated = true
                            // If still more substeps to go, break for now until the next attempt to step
                            if (stepElement.activeSubstepIndex > 0) break
                        }
                    }

                    stepElement.classList.remove("active")
                    stepDeactivated = true

                    if (!stepElement.classList.contains("auto")) {
                        break
                    }
                }
            }

            // If the last element we deactivate is auto, that means we should actually go further and retreat to the
            // previous section / slide. Even though we actually changed stuff here, just say it wasn't handled so the
            // calling code will continue.
            if (!stepElements.first().classList.contains("active") && stepElements.first().classList.contains("auto")) {
                false
            } else {
                stepDeactivated
            }
        }
    }

    DisposableEffect(containerElement) {
        val contentContainer = containerElement ?: return@DisposableEffect onDispose {}
        stepElements.clear()
        stepElements.addAll(contentContainer.getElementsByClassName("step")
            .asList()
            .filterIsInstance<HTMLElement>()
        )

        fun onStepsChanged() {
            stepElements.firstOrNull()?.let { stepElement ->
                stepElement.enqueueWithDelayIfAuto { tryStep(true) }
            }
        }
        onStepsChanged()

        val observer = MutationObserver { mutations, observer ->
            mutations.forEach { mutation ->
                stepElements.removeAll(mutation.removedNodes.asList().filterIsInstance<HTMLElement>().flatMap {
                    it.getElementsByClassName("step").asList().filterIsInstance<HTMLElement>()
                })
                mutation.addedNodes.asList().filterIsInstance<HTMLElement>().flatMap {
                    it.getElementsByClassName("step").asList().filterIsInstance<HTMLElement>()
                }.forEach { stepElement ->
                    // Some elements are repeated multiple times (by different parents I guess?)
                    if (stepElement !in stepElements) stepElements.add(stepElement)
                }
                onStepsChanged()
            }
        }
        observer.observe(contentContainer, MutationObserverInit(childList = true, subtree = true))
        onDispose { observer.disconnect() }
    }

    DisposableEffect(Unit) {
        val manager = EventListenerManager(window)

        manager.addEventListener("resize") {
            scale = calculateScale()
        }

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

                    val args = DirectionArgs(forward = origDelta > 0)
                    (ctx.data.getValue<SlideEvents>().onNavigating as EventImpl).invoke(args)

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
                "ArrowLeft" -> {
                    containerElement!!.deactivateAllSteps()
                    tryNavigateToSlide(-1)
                }
                "ArrowRight" -> {
                    containerElement!!.activateAllSteps()
                    tryNavigateToSlide(+1)
                }
                " " -> {
                    val args = DirectionArgs(forward = !event.shiftKey)
                    handled = tryStep(args.forward)

                    if (!handled) {
                        handled =
                            (ctx.data.getValue<SlideEvents>().onStepRequested as EventImpl)
                                .asSequence(args)
                                .any { it }

                        if (!handled) {
                            tryNavigateToSlide(if (args.forward) +1 else -1)
                        }
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

    @Composable
    fun Modifier.slidingAnimation() = animation(
        SlideHorizKeyframes.toAnimation(
            AnimSpeeds.Quick.toCssUnit(),
            timingFunction = AnimationTimingFunction.EaseInOut,
        )
    )

    Box(SlideBackgroundStyle.toModifier(), contentAlignment = Alignment.Center, ref = ref { containerElement = it }) {
        Box(SlideLayoutStyle.toModifier().setVariable(SlideScaleVar, scale)) {
            Box(
                Modifier.fillMaxSize()
                    .padding(2.4.cssRem)
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
                                (ctx.data.getValue<SlideEvents>().onNavigated as EventImpl).invoke(DirectionArgs(forward = true))
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
                                (ctx.data.getValue<SlideEvents>().onNavigated as EventImpl).invoke(DirectionArgs(forward = false))
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
                            .onAnimationStart {
                                // We are going back to a previous slide, so show all steps
                                containerElement!!.activateAllSteps()
                            }
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
                contentAlignment = Alignment.Center,
            ) {
                CompositionLocalProvider(SlideUtilsLocal provides object : SlideUtils {
                    override fun cancelRunningSteps() {
                        cancelHandle?.cancel()
                        cancelHandle = null
                    }
                }) {
                    content()
                }
            }
        }
    }
    Div(
        SlidesProgressStyle.toModifier()
            .setVariable(SlidesProgressVar, (progressPercent * 100).roundToInt().percent)
            .toAttrs()
    )
}
