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
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.time.Duration

// A step is a small unit of progress. Pressing SPACE repeatedly will keep activating the next step.
// If all steps on a page are active, then either the presentation will proceed to the next slide section (if more than
// one) or to the next slide (unless on the last slide).

// See also: SlideLayout, which is responsible for managing steps

object StepTypes {
    const val FadeIn = "fade-in"
    const val OneAtATime = "one-at-a-time"
}

fun Modifier.step(stepType: String = StepTypes.FadeIn, auto: Boolean = false, index: Int? = null) =
    classNames(buildList {
        add("step")
        add(stepType)
        if (auto) add("auto")
    }).thenIfNotNull(index) { Modifier.attr("data-step-index", it.toString())}

fun Modifier.step(stepType: String = StepTypes.FadeIn, delay: Duration, index: Int? = null) =
    attr("data-step-delay", "${delay.inWholeMilliseconds}")
        .step(stepType, auto = true, index = index)


@InitSilk
fun initStepStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase(".step.fade-in") {
            Modifier
                .transition(Transition.of("opacity", AnimSpeeds.Fast.toCssUnit()))
                .opacity(0)
        }

        registerStyleBase(".step.fade-in.active") {
            Modifier
                .opacity(1)
        }

        registerStyleBase(".step.one-at-a-time:not(.current)") {
            Modifier.display(DisplayStyle.None)
        }
    }
}

fun HTMLElement.activateAllSteps(): Boolean {
    var anyActivated = false
    val stepElements = getElementsByClassName("step").asList().filterIsInstance<HTMLElement>()
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
    val stepElements = getElementsByClassName("step").asList().filterIsInstance<HTMLElement>()
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
