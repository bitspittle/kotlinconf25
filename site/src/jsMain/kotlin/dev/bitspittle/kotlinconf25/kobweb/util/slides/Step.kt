package dev.bitspittle.kotlinconf25.kobweb.util.slides

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIfNotNull
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.activeSubstepIndex
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.substepCount
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.util.toCssUnit
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.time.Duration

// A step is a small unit of progress. Pressing SPACE repeatedly will keep activating the next step.
// If all steps on a page are active, then either the presentation will proceed to the next slide section (if more than
// one) or to the next slide (unless on the last slide).

// See also: SlideLayout, which is responsible for managing steps

object StepTypes {
    /** Slide down while fading in */
    const val FadeDown = "fade-down"
    /** Fade in when the step becomes active */
    const val FadeIn = "fade-in"
    /** Fade in when the step becomes active and then fade to 50% on the next step */
    const val FadeInThenSemiOut = "fade-in-then-semi-out"
    /** Slide left while fading in */
    const val FadeLeft = "fade-left"
    /** Slide right while fading in */
    const val FadeRight = "fade-right"
    /** Slide up while fading in */
    const val FadeUp = "fade-up"
    /** Show one step at a time. When not current, this step will not be laid out at all (display: none) */
    const val OneAtATime = "one-at-a-time"

    const val Default = FadeIn
}

fun Modifier.step(stepType: String = StepTypes.Default, auto: Boolean = false, order: Int? = null) =
    classNames(buildList {
        add("step")
        add(stepType)
        if (auto) add("auto")
    }).thenIfNotNull(order) { Modifier.attr("data-step-order", it.toString())}

fun Modifier.step(stepType: String = StepTypes.Default, delay: Duration, order: Int? = null) =
    attr("data-step-delay", "${delay.inWholeMilliseconds}")
        .step(stepType, auto = true, order = order)

// Useful for elements that already have step information set up (i.e. code blocks with highlight lines)
fun Modifier.stepOrder(order: Int) = attr("data-step-order", order.toString())

val DefaultStepSpeed = AnimSpeeds.Fast.toCssUnit()

@InitSilk
fun initStepStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase(".step.${StepTypes.FadeIn}") {
            Modifier
                .transition(Transition.of("opacity", DefaultStepSpeed))
                .opacity(0)
        }

        registerStyleBase(".step.${StepTypes.FadeIn}.active") {
            Modifier
                .opacity(1)
        }

        registerStyleBase(".step.${StepTypes.FadeUp}") {
            Modifier
                .transition(Transition.group(listOf("opacity", "translate"), DefaultStepSpeed))
                .opacity(0)
                .translateY(40.px)
        }

        registerStyleBase(".step.${StepTypes.FadeUp}.active") {
            Modifier
                .opacity(1)
                .translateY(0.px)
        }

        registerStyleBase(".step.${StepTypes.FadeDown}") {
            Modifier
                .transition(Transition.group(listOf("opacity", "translate"), DefaultStepSpeed))
                .opacity(0)
                .translateY((-40).px)
        }

        registerStyleBase(".step.${StepTypes.FadeDown}.active") {
            Modifier
                .opacity(1)
                .translateY(0.px)
        }

        registerStyleBase(".step.${StepTypes.FadeLeft}") {
            Modifier
                .transition(Transition.group(listOf("opacity", "translate"), DefaultStepSpeed))
                .opacity(0)
                .translateX(40.px)
        }

        registerStyleBase(".step.${StepTypes.FadeLeft}.active") {
            Modifier
                .opacity(1)
                .translateX(0.px)
        }

        registerStyleBase(".step.${StepTypes.FadeRight}") {
            Modifier
                .transition(Transition.group(listOf("opacity", "translate"), DefaultStepSpeed))
                .opacity(0)
                .translateX((-40).px)
        }

        registerStyleBase(".step.${StepTypes.FadeRight}.active") {
            Modifier
                .opacity(1)
                .translateX(0.px)
        }

        registerStyleBase(".step.${StepTypes.FadeInThenSemiOut}") {
            Modifier
                .transition(Transition.of("opacity", DefaultStepSpeed))
        }

        registerStyleBase(".step.${StepTypes.FadeInThenSemiOut}:not(.active)") {
            Modifier
                .opacity(0)
        }

        registerStyleBase(".step.${StepTypes.FadeInThenSemiOut}.active.current") {
            Modifier
                .opacity(1)
        }

        registerStyleBase(".step.${StepTypes.FadeInThenSemiOut}.active:not(.current)") {
            Modifier
                .opacity(0.5)
        }

        registerStyleBase(".step.${StepTypes.OneAtATime}:not(.current):not(:has(.current))") {
            Modifier.display(DisplayStyle.None)
        }
    }
}

fun HTMLElement.getOrderedSteps(): List<HTMLElement> {
    fun HTMLElement.getInheritedStepOrder(): Int? {
        var current: HTMLElement? = this
        while (current != null) {
            current.getAttribute("data-step-order")?.toIntOrNull()?.let { return it }
            current = current.parentElement as? HTMLElement
        }
        return null
    }

    return getElementsByClassName("step")
        .asList()
        .filterIsInstance<HTMLElement>()
        .sortedBy { it.getInheritedStepOrder() ?: 0 }
}

fun HTMLElement.activateAllSteps(): Boolean {
    var anyActivated = false
    val stepElements = getOrderedSteps()
    stepElements.forEach { step ->
        if (!step.classList.contains("active")) {
            step.classList.add("active")
            anyActivated = true
        }
        step.classList.remove("current")

        if (step.substepCount > 0) {
            step.activeSubstepIndex = step.substepCount - 1
        }
    }
    stepElements.lastOrNull()?.classList?.add("current")
    return anyActivated
}

fun HTMLElement.deactivateAllSteps(): Boolean {
    var anyDeactivated = false
    val stepElements = getOrderedSteps()
    stepElements.forEach { step ->
        if (step.classList.contains("active")) {
            step.classList.remove("active")
            anyDeactivated = true
        }
        step.classList.remove("current")

        if (step.substepCount > 0) {
            step.activeSubstepIndex = 0
        }
    }

    return anyDeactivated
}
