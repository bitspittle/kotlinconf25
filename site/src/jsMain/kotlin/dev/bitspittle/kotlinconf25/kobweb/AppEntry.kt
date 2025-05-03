package dev.bitspittle.kotlinconf25.kobweb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import dev.bitspittle.kotlinconf25.kobweb.style.vars.DividerColor
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

@InitSilk
fun initSilk(ctx: InitSilkContext) {
    ctx.config.initialColorMode = ColorMode.loadFromLocalStorage() ?: ColorMode.DARK
    ctx.stylesheet.apply {
        registerStyleBase("html") {
            // Slide text is big!
            Modifier.fontSize(32.px)
        }

        registerStyleBase("body") {
            Modifier
                .fontFamily("Inter", "sans-serif")
                .overflowWrap(OverflowWrap.BreakWord)
        }

        registerStyleBase("code, pre") {
            Modifier.fontFamily("Roboto Mono", "monospace")
        }

        val headerCommon = Modifier
            .fontWeight(FontWeight.SemiBold)
            // For slides, we use h1 etc. simply to set text sizes, not for section flows like in an article
            .margin(0.px)
            .lineHeight(1.1)

        registerStyleBase("h1") {
            headerCommon.fontSize(4.cssRem)
        }

        registerStyleBase("h2") {
            headerCommon.fontSize(3.5.cssRem)
        }

        registerStyleBase("h3") {
            headerCommon.fontSize(2.75.cssRem)
        }

        registerStyleBase("h4") {
            headerCommon.fontSize(1.75.cssRem)
        }
    }
}

val AppSurfaceStyle = CssStyle.base {
    Modifier
        .setVariable(
            DividerColor,
            if (colorMode.isDark) {
                Color.rgba(238, 238, 238, 0.2f)
            } else {
                Color.rgba(17, 17, 17, 0.2f)
            }
        )
        .width(100.vw).height(100.vh)
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) {
            colorMode.saveToLocalStorage()
        }

        Surface(
            SmoothColorStyle.toModifier().then(AppSurfaceStyle.toModifier())
        ) {
            content()
        }
    }
}
