package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.markdown

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.percent

@InitRoute
fun initCodePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Code"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun CodePage() {
    Box(Modifier.step(StepTypes.OneAtATime, auto = true).width(50.percent).height(60.percent), contentAlignment = Alignment.BottomCenter) {
        CodeBlock(
            //language=markdown
            """
            Today is ${'$'}{.components.widgets.time.CurrentDay}
            
            {{{ .components.widgets.time.Clock }}}
            """.trimIndent(),
            lang = "markdown",
            highlightLines = "0|1|3",
            preModifier = Modifier.fillMaxWidth()
        )
    }
    Box(Modifier.step(StepTypes.OneAtATime).width(50.percent).height(60.percent), contentAlignment = Alignment.BottomCenter) {
        CodeBlock(
            //language=markdown
            """
            ---
            imports:
              - .components.widgets.time.CurrentDay
              - .components.widgets.time.Clock
            ---
            
            Today is ${'$'}{CurrentDay}
            
            {{{ Clock }}}
            """.trimIndent(),
            lang = "markdown",
            highlightLines = "2-4|7,9",
            preModifier = Modifier.fillMaxWidth()
        )
    }
}
