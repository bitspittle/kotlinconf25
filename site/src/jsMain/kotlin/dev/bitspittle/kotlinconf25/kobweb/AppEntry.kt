package dev.bitspittle.kotlinconf25.kobweb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.layer
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.layer.SilkLayer
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import dev.bitspittle.kotlinconf25.kobweb.style.vars.DividerColorVar
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
            Modifier.fontSize(40.px).lineHeight(1.3)
        }

        registerStyleBase("body") {
            Modifier
                .fontFamily("Inter", "sans-serif")
                .overflowWrap(OverflowWrap.BreakWord)
                .userSelect(UserSelect.None)
        }

        registerStyleBase("code, pre") {
            Modifier.fontFamily("Roboto Mono", "monospace")
                .fontSize(32.px)
        }

        val headerCommon = Modifier
            .fontWeight(FontWeight.SemiBold)
            // For slides, we use h1 etc. simply to set text sizes, not for section flows like in an article
            .margin(0.px)
            .lineHeight(1.1)

        registerStyleBase("h1") {
            // Biggest font is used on header slides -- feels like it needs a little more vertical space to breathe
            headerCommon.fontSize(3.7.cssRem).lineHeight(1.2)
        }

        registerStyleBase("h2") {
            headerCommon.fontSize(3.2.cssRem)
        }

        registerStyleBase("h3") {
            headerCommon.fontSize(2.2.cssRem)
        }

        registerStyleBase("h4") {
            headerCommon.fontSize(1.4.cssRem).fontWeight(FontWeight.Normal)
        }

        registerStyleBase("h5") {
            headerCommon.fontSize(1.cssRem).fontWeight(FontWeight.Normal)
        }

        registerStyleBase("h6") {
            headerCommon.fontSize(0.8.cssRem).fontWeight(FontWeight.Normal)
        }

        layer(SilkLayer.RESET) {
            registerStyleBase("ul") {
                Modifier.marginBlock(0.px)
            }
        }
    }
}

val AppSurfaceStyle = CssStyle.base {
    Modifier
        .setVariable(
            DividerColorVar,
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
