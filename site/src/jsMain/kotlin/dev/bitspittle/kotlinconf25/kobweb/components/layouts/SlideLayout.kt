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
import com.varabyte.kobweb.compose.css.functions.url
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
import com.varabyte.kobweb.core.data.get
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.bindings.prismjs.Prism
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.activeSubstepIndex
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.substepCount
import dev.bitspittle.kotlinconf25.kobweb.slides
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import dev.bitspittle.kotlinconf25.kobweb.util.slides.activateAllSteps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.deactivateAllSteps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.getOrderedSteps
import dev.bitspittle.kotlinconf25.kobweb.util.toCssUnit
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement
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
        .transition(Transition.of("width", AnimSpeeds.Fast.toCssUnit(), TransitionTimingFunction.Ease))
}

private enum class SlidingHorizDirection {
    IN_FROM_LEFT,
    IN_FROM_RIGHT,
    OUT_TO_LEFT,
    OUT_TO_RIGHT,

    // Necessary to prevent one frame flicker as a new slide comes in
    HIDING;
}

class HeaderBackground(val path: String)

interface EventArgs

abstract class Event<A : EventArgs, R> {
    abstract fun add(callback: (A) -> R): (A) -> R
    abstract fun remove(callback: (A) -> R)
    operator fun plusAssign(callback: (A) -> R) { add(callback) }
    operator fun minusAssign(callback: (A) -> R) { remove(callback) }
}

class EventImpl<A : EventArgs, R> : Event<A, R>() {
    private val callbacks = mutableListOf<(A) -> R>()
    override fun add(callback: (A) -> R): (A) -> R { callbacks.add(callback); return callback }
    override fun remove(callback: (A) -> R) { callbacks.remove(callback) }

    fun asSequence(param: A): Sequence<R> = callbacks.asReversed().asSequence().map { it(param) }

    operator fun invoke(param: A) {
        // Convert to list which consumes the sequence as a side effect
        asSequence(param).toList()
    }
}

interface SlideUtils {
    fun cancelRunningSteps()
    fun refreshSteps()
    fun deactivateAllSteps()
    fun activateAllSteps()

}

open class SlideLayoutScope(val slideUtils: SlideUtils)

@Suppress("unused")
object EmptyEventArgs : EventArgs
class DirectionArgs(val forward: Boolean) : EventArgs

class SlideEvents {
    /** Fired the moment a slide navigation begins */
    val onNavigating: Event<DirectionArgs, Unit> = EventImpl()
    /** Fired the last frame before the current slide is about to exit the screen. */
    val onExiting: Event<DirectionArgs, Unit> = EventImpl()
    /** Fired when we JUST entered a new slide (still transitioning in) */
    val onEntered: Event<DirectionArgs, Unit> = EventImpl()
    /**
     * Fired by the parent layout giving children a chance to handle a request to step.
     *
     * Children should return true to indicate they handled the step; false otherwise. If they don't handle the step
     * action, we will.
     * */
    val onStepRequested: Event<DirectionArgs, Boolean> = EventImpl()
}

@InitRoute
fun initSlideLayout(ctx: InitRouteContext) {
    ctx.data.add(SlideEvents())
}

@Layout
@Composable
fun SlideLayout(ctx: PageContext, content: @Composable SlideLayoutScope.() -> Unit) {
    fun getCurrentSlidePath() = ctx.route.path.substringAfter("/")
    fun getCurrentSlideIndex() = AppGlobals.slides.indexOf(getCurrentSlidePath())
    fun getNumSlides() = AppGlobals.slides.size

    var progressPercent by remember { mutableStateOf(0f) }
    LaunchedEffect(ctx.route.path) {
        progressPercent = getCurrentSlideIndex() / (getNumSlides() - 1).toFloat()
    }

    fun calculateScale(): Float {
        return min(
            window.innerWidth.toFloat() / TARGET_WIDTH,
            window.innerHeight.toFloat() / TARGET_HEIGHT,
        )
    }

    var backgroundOpacity by remember(ctx.route.path) { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(calculateScale()) }
    var slidingDirection by remember { mutableStateOf<SlidingHorizDirection?>(null) }
    var targetSlide by remember { mutableStateOf<String?>(null) }
    var containerElement by remember { mutableStateOf<HTMLElement?>(null) }
    val stepElements = remember { mutableListOf<HTMLElement>() }

    LaunchedEffect(containerElement, ctx.route.path) {
        val containerElement = containerElement ?: return@LaunchedEffect
        Prism.highlightAllUnder(containerElement)
    }

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
                getAttribute("data-step-delay")?.toIntOrNull()?.milliseconds ?: AnimSpeeds.VeryQuick
            enqueueWithDelay(delay, action)
        }
    }

    fun tryStep(forward: Boolean): Boolean {
        if (stepElements.isEmpty()) return false

        return if (forward) {
            var stepActivated = false
            var currentStepElement: HTMLElement? = null
            for (stepElement in stepElements) {
                if (stepElement.classList.contains("current")) {
                    currentStepElement = stepElement
                }
                if (!stepElement.classList.contains("active")) {
                    if (stepActivated) {
                        stepElement.enqueueWithDelayIfAuto { tryStep(forward) }
                        break
                    } else {
                        stepElement.classList.add("active", "current")
                        currentStepElement?.classList?.remove("current")
                        currentStepElement = null

                        stepActivated = true
                    }
                }

                // Handle multi-part steps if this is one (basically, even if it is active, it keeps consuming steps)
                if (stepElement.substepCount > 1) {
                    if (stepElement.activeSubstepIndex < stepElement.substepCount - 1) {
                        stepElement.activeSubstepIndex++
                        stepActivated = true
                        // If we're on the last subset, don't break, as the next step might be an "auto" one
                        if (stepElement.activeSubstepIndex < stepElement.substepCount - 1) {
                            break
                        }
                    }
                }
            }
            stepActivated
        } else {
            var stepDeactivated = false
            // Remove all grouped auto steps in one fell swoop
            var stopOnNextActiveElement = false
            for (stepElement in stepElements.asReversed()) {
                if (stepElement.classList.contains("active")) {
                    if (stopOnNextActiveElement) {
                        stepElement.classList.add("current")
                        break
                    }
                    // Handle multi-part steps if this is one
                    if (stepElement.substepCount > 1) {
                        if (stepElement.activeSubstepIndex > 0) {
                            stepElement.activeSubstepIndex--
                            stepDeactivated = true
                            // If still more substeps to go, break for now until the next attempt to step
                            if (stepElement.activeSubstepIndex > 0) break
                        }
                    }

                    stepElement.classList.remove("active", "current")
                    stepDeactivated = true

                    if (!stepElement.classList.contains("auto")) {
                        stopOnNextActiveElement = true
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

    fun takeFirstStepIfAuto() {
        stepElements.firstOrNull()?.let { stepElement ->
            stepElement.enqueueWithDelayIfAuto { tryStep(true) }
        }
    }

    fun repopulateStepElements() {
        val containerElement = containerElement ?: return

        stepElements.clear()
        stepElements.addAll(containerElement.getOrderedSteps())

        takeFirstStepIfAuto()
    }

    val slideLayoutScope = remember {
        SlideLayoutScope(object : SlideUtils {
            override fun cancelRunningSteps() {
                cancelHandle?.cancel()
                cancelHandle = null
            }

            override fun refreshSteps() {
                repopulateStepElements()
            }

            override fun deactivateAllSteps() {
                containerElement!!.deactivateAllSteps()
                Prism.highlightAllUnder(containerElement!!)
            }

            override fun activateAllSteps() {
                containerElement!!.activateAllSteps()
                Prism.highlightAllUnder(containerElement!!)
            }
        })
    }
    val slideUtils = slideLayoutScope.slideUtils

    LaunchedEffect(containerElement) {
        repopulateStepElements()
    }

    DisposableEffect(containerElement) {
        val containerElement = containerElement ?: return@DisposableEffect onDispose { }

        val manager = EventListenerManager(containerElement)
        manager.addEventListener("resize") {
            scale = calculateScale()
        }
        manager.addEventListener("keydown") { event ->
            fun tryNavigateToSlide(delta: Int): Boolean {
                if (delta == 0) return false
                val origDelta = delta
                var delta = delta
                // If we request to navigate while mid-navigation, use the next target slide as the starting point
                var desiredSlide: String? = targetSlide ?: "/${ctx.route.path.substringAfter("/")}"
                while (delta > 0 && desiredSlide != null) {
                    desiredSlide = AppGlobals.slides.next(desiredSlide)
                    --delta
                }
                while (delta < 0 && desiredSlide != null) {
                    desiredSlide = AppGlobals.slides.prev(desiredSlide)
                    ++delta
                }
                return if (desiredSlide != null) {
                    targetSlide = desiredSlide
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
                    true
                } else
                    false
            }

            var handled = true
            when (val key = (event as KeyboardEvent).key) {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                    // 1 -> 0%
                    // 2 -> 11.1%
                    // ...
                    // 0 -> 100%
                    val keyNum = key.toInt()
                    val percent = if (keyNum == 0) 1f else (keyNum - 1) * .111f

                    val targetSlide = ((getNumSlides() - 1) * percent).roundToInt()
                    val delta = targetSlide - getCurrentSlideIndex()

                    if (delta != 0) {
                        slideUtils.cancelRunningSteps()
                        tryNavigateToSlide(delta)
                    }
                }

                "ArrowUp" -> {
                    if (stepElements.isNotEmpty() && stepElements.firstOrNull { it.classList.contains("active") && !it.classList.contains("auto") } != null) {
                        containerElement.deactivateAllSteps()
                        takeFirstStepIfAuto()
                    } else {
                        handled = false
                    }
                }
                "ArrowDown" -> {
                    if (stepElements.isNotEmpty() && !stepElements.last().classList.contains("active")) {
                        containerElement.activateAllSteps()
                    } else {
                        handled = false
                    }
                }
                "ArrowLeft" -> {
                    if (!event.shiftKey && !event.altKey && !event.ctrlKey && !event.metaKey) {
                        containerElement.deactivateAllSteps()
                        tryNavigateToSlide(-1)
                    } else {
                        handled = false
                    }
                }
                "ArrowRight" -> {
                    if (!event.shiftKey && !event.altKey && !event.ctrlKey && !event.metaKey) {
                        containerElement.activateAllSteps()
                        tryNavigateToSlide(+1)
                    } else {
                        handled = false
                    }
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

                    if (handled) {
                        window.invokeLater { Prism.highlightAllUnder(containerElement) }
                    }
                }
                "Home" -> {
                    if (event.shiftKey) {
                        slideUtils.cancelRunningSteps()
                        if (!tryNavigateToSlide(-getCurrentSlideIndex())) {
                            // If we failed, we're already on the first slide
                            slideUtils.deactivateAllSteps()
                        }
                    } else {
                        handled = false
                    }
                }
                "End" -> {
                    if (event.shiftKey) {
                        slideUtils.cancelRunningSteps()
                        if (!tryNavigateToSlide(getNumSlides() - getCurrentSlideIndex() - 1)) {
                            // If we failed, we're already on the last slide
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

    @Composable
    fun Modifier.slidingAnimation() = animation(
        SlideHorizKeyframes.toAnimation(
            AnimSpeeds.Fast.toCssUnit(),
            timingFunction = AnimationTimingFunction.EaseOut,
        )
    )

    Box(SlideBackgroundStyle.toModifier(), contentAlignment = Alignment.Center, ref = ref {
        it.tabIndex = 0
        it.focus()
        containerElement = it
    }) {
        ctx.data.get<HeaderBackground>()?.let { headerBackground ->
            Box(
                Modifier.fillMaxSize()
                    .backgroundImage(url(headerBackground.path))
                    .backgroundSize(BackgroundSize.Cover)
                    .backgroundRepeat(BackgroundRepeat.NoRepeat)
                    .backgroundPosition(BackgroundPosition.of(CSSPosition.Center))
                    .opacity(backgroundOpacity)
                    .transition(
                        Transition.of(
                            "opacity",
                            duration = AnimSpeeds.Quick.toCssUnit(),
                            timingFunction = AnimationTimingFunction.Ease
                        )
                    ),
                ref = ref { window.invokeLater { backgroundOpacity = 1f } }
            )
        }

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
                            .onAnimationStart { backgroundOpacity = 0f }
                            .onAnimationEnd {
                                (ctx.data.getValue<SlideEvents>().onExiting as EventImpl).invoke(DirectionArgs(forward = true))
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingHorizDirection.HIDING
                                containerElement!!.focus()
                                window.invokeLater {
                                    (ctx.data.getValue<SlideEvents>().onEntered as EventImpl).invoke(DirectionArgs(forward = true))
                                    repopulateStepElements()
                                    slidingDirection = SlidingHorizDirection.IN_FROM_RIGHT
                                }
                            }
                            .slidingAnimation()
                    }
                    .thenIf(slidingDirection == SlidingHorizDirection.OUT_TO_RIGHT) {
                        Modifier
                            .setVariable(SlideHorizFromOpacityVar, 1f)
                            .setVariable(SlideHorizToOpacityVar, 0f)
                            .setVariable(SlideHorizFromTranslatePercentVar, 0.percent)
                            .setVariable(SlideHorizToTranslatePercentVar, 100.percent)
                            .onAnimationStart { backgroundOpacity = 0f }
                            .onAnimationEnd {
                                (ctx.data.getValue<SlideEvents>().onExiting as EventImpl).invoke(DirectionArgs(forward = false))
                                ctx.router.tryRoutingTo(targetSlide!!)
                                targetSlide = null
                                slidingDirection = SlidingHorizDirection.HIDING
                                containerElement!!.focus()
                                window.invokeLater {
                                    (ctx.data.getValue<SlideEvents>().onEntered as EventImpl).invoke(DirectionArgs(forward = false))
                                    repopulateStepElements()
                                    slidingDirection = SlidingHorizDirection.IN_FROM_LEFT
                                }
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
                content(slideLayoutScope)
            }
        }
    }
    Div(
        SlidesProgressStyle.toModifier()
            .setVariable(SlidesProgressVar, (progressPercent * 100).roundToInt().percent)
            .toAttrs()
    )
}
