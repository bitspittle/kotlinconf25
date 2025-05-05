package dev.bitspittle.kotlinconf25.kobweb.components.layouts

import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import dev.bitspittle.kotlinconf25.kobweb.style.AnimSpeeds
import dev.bitspittle.kotlinconf25.kobweb.util.toCssUnit
import kotlin.time.Duration

// A step is a small unit of progress. Pressing SPACE repeatedly will keep activating the next step.
// If all steps on a page are active, then either the presentation will proceed to the next slide section (if more than
// one) or to the next slide (unless on the last slide).

// See also: SlideLayout, which is responsible for managing steps

private const val DEFAULT_STEP_TYPE = "fade-in"

fun Modifier.step(stepType: String = DEFAULT_STEP_TYPE, auto: Boolean = false) =
    classNames(buildList {
        add("step")
        add(stepType)
        if (auto) add("auto")
    })

fun Modifier.step(stepType: String = DEFAULT_STEP_TYPE, delay: Duration) =
    attr("data-step-delay", "${delay.inWholeMilliseconds}")
        .step(stepType, auto = true)


@InitSilk
fun initStepStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase(".step.fade-in") {
            Modifier
                .transition(Transition.of("opacity", AnimSpeeds.Quick.toCssUnit()))
                .opacity(0)
        }

        registerStyleBase(".step.fade-in.active") {
            Modifier
                .opacity(1)
        }
    }
}