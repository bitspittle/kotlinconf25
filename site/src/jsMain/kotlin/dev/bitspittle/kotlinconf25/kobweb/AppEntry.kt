package dev.bitspittle.kotlinconf25.kobweb

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.overflowWrap
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.bitspittle.kotlinconf25.kobweb.components.style.DividerColor

@InitSilk
fun initSilk(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase("body") {
            Modifier
                .fontFamily("Inter", "sans-serif")
                .overflowWrap(OverflowWrap.BreakWord)
        }

        registerStyleBase("code, pre") {
            Modifier.fontFamily("Roboto Mono", "monospace")
        }
    }
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    val colorMode = ColorMode.current

    SilkApp {
        Surface(
            SmoothColorStyle.toModifier()
                .setVariable(
                    DividerColor,
                    if (colorMode.isDark) {
                        Color.rgba(238, 238, 238, 0.2f)
                    } else {
                        Color.rgba(17, 17, 17, 0.2f)
                    }
                )
        ) {
            content()
        }
    }
}
