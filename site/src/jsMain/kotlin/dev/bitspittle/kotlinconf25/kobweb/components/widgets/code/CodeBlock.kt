package dev.bitspittle.kotlinconf25.kobweb.components.widgets.code

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.MinWidth
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import dev.bitspittle.kotlinconf25.kobweb.style.vars.DividerColorVar
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

val CodeBlockStyle = CssStyle.base {
    Modifier
        .borderRadius(10.px)
        .overflow { x(Overflow.Auto) }
        .border(1.px, LineStyle.Solid, DividerColorVar.value())
        .padding(1.em)
}

/**
 * Creates a code block that is colored by PrismJs
 */
// Note: To enable this widget to work, we needed to add PrismJs support to this project. See the kobweb
// block in our build.gradle.kts file to see how this was done.
@Composable
fun CodeBlock(code: String, modifier: Modifier = Modifier, lang: String? = "kotlin") {
    Pre(CodeBlockStyle.toModifier().then(SmoothColorStyle.toModifier()).toAttrs()) {
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
            Text(code)
        }
    }
}