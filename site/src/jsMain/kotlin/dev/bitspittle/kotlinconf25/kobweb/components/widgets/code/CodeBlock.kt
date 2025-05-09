package dev.bitspittle.kotlinconf25.kobweb.components.widgets.code

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.BoxSizing
import com.varabyte.kobweb.compose.css.MinWidth
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.bindings.prismjs.Prism
import dev.bitspittle.kotlinconf25.kobweb.style.vars.DividerColorVar
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import org.w3c.dom.MutationObserver
import org.w3c.dom.MutationObserverInit

private fun Modifier.defaultPadding() = padding(1.em)

const val ATTR_NAME_SUBSTEP_COUNT = "data-substep-count"
const val ATTR_NAME_ACTIVE_SUBSTEP = "data-active-substep"

var HTMLElement.substepCount
    get() = getAttribute(ATTR_NAME_SUBSTEP_COUNT)?.toIntOrNull() ?: 0
    set(value) =
        if (value > 1) setAttribute(ATTR_NAME_SUBSTEP_COUNT, value.toString())
        else removeAttribute(ATTR_NAME_SUBSTEP_COUNT)

var HTMLElement.activeSubstepIndex
    get() = getAttribute(ATTR_NAME_ACTIVE_SUBSTEP)?.toIntOrNull() ?: 0
    set(value) =
        if (value > 0) setAttribute(ATTR_NAME_ACTIVE_SUBSTEP, value.toString())
        else removeAttribute(ATTR_NAME_ACTIVE_SUBSTEP
    )

val CodeBlockStyle = CssStyle.base {
    Modifier
        .borderRadius(10.px)
        .maxSize(100.percent)
        .boxSizing(BoxSizing.BorderBox)
        // Prismjs sometimes causes unnecessary scrollbars to appear. Anyway, we shouldn't allow scrolling because
        // this is a slide presentation and users can't scroll anyway.
        .overflow { x(Overflow.Hidden); y(Overflow.Auto) }
        .border(1.px, LineStyle.Solid, DividerColorVar.value())
        .defaultPadding()
}

@InitSilk
fun initStepStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase("pre[data-line]") {
            // Prism.js adds a bunch of left padding room for line numbers that we never show. Override it.
            Modifier.defaultPadding()
        }
    }
}

/**
 * Creates a code block that is colored by PrismJs
 */
// Note: To enable this widget to work, we needed to add PrismJs support to this project. See the kobweb
// block in our build.gradle.kts file to see how this was done.
@Composable
fun CodeBlock(code: String, modifier: Modifier = Modifier, lang: String? = "kotlin", highlightLines: String? = null) {
    var preElement by remember { mutableStateOf<HTMLElement?>(null) }
    var codeElement by remember { mutableStateOf<HTMLElement?>(null) }

    val highlightLineParts = remember(highlightLines) { highlightLines?.split("|") }
    var activeHighlightPart by remember(highlightLineParts) { mutableStateOf<String?>(null) }
    var activePartIndex by remember(preElement) { mutableStateOf(0) }

    DisposableEffect(preElement) {
        val preElement = preElement ?: return@DisposableEffect onDispose {}
        val observer = MutationObserver { mutations, observer ->
            activePartIndex = preElement.activeSubstepIndex
        }
        observer.observe(preElement, MutationObserverInit(attributeFilter = arrayOf(ATTR_NAME_ACTIVE_SUBSTEP)))
        onDispose {
            observer.disconnect()
        }
    }

    LaunchedEffect(highlightLineParts, activePartIndex) {
        activeHighlightPart = highlightLineParts?.getOrNull(activePartIndex)?.takeIf { it != "0" }.orEmpty()
    }

    LaunchedEffect(codeElement, activeHighlightPart) {
        val codeElement = codeElement ?: return@LaunchedEffect
        Prism.highlightElement(codeElement)
    }

    Pre(CodeBlockStyle.toModifier()
        .then(SmoothColorStyle.toModifier())
        .thenIfNotNull(highlightLineParts?.size) {
            if (it >= 2) {
                Modifier
                    .attr("data-substep-count", it.toString())
                    .classNames("step", "highlight-lines")
            }
            else Modifier
        }
        .thenIfNotNull(activePartIndex) { Modifier.attr("data-active-substep", it.toString()) }
        .thenIfNotNull(activeHighlightPart) { Modifier.attr("data-line", it) }
        .toAttrs()
    ) {
        registerRefScope(ref { preElement = it})
        Code(
            attrs = SmoothColorStyle.toModifier()
                // Set min width so that `diff-highlight` coverts the entire width even when the code is scrollable
                .display(DisplayStyle.Block).minWidth(MinWidth.MaxContent)
                // The above style messes up text sizes inside code blocks that can
                // scroll horizontally (but only on iOS...)
                .styleModifier { property("-webkit-text-size-adjust", 100.percent) }
                .classNames("language-${lang ?: "none"}")
                .thenIf(lang?.startsWith("diff") == true, Modifier.classNames("diff-highlight"))
                .then(modifier)
                .toAttrs()
        ) {
            registerRefScope(ref { codeElement = it })
            Text(code)
        }
    }
}