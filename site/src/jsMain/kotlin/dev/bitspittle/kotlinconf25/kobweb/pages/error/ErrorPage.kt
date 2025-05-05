package dev.bitspittle.kotlinconf25.kobweb.pages.error

import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import org.jetbrains.compose.web.dom.Text

@InitKobweb
fun setErrorPage(ctx: InitKobwebContext) {
    ctx.router.setErrorPage(
        "dev.bitspittle.kotlinconf25.kobweb.components.layouts.HeaderSlideLayout",
    ) {
        Text("Page Not Found")
    }
}