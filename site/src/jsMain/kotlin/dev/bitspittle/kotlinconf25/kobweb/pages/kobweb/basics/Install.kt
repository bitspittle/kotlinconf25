package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initInstallPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        Text("Install ")
        SiteColors.KobwebBlue.Text("Kobweb")
    })
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun InstallPage() {
    CodeBlock(
        //language=bash
        """
            # Homebrew: Mac / Linux
            $ brew install varabyte/tap/kobweb
            
            # Scoop: Windows
            $ scoop install varabyte/kobweb
            
            # SDKMAN! Mac / Windows / *nix
            $ sdk install kobweb
        """.trimIndent(),
        lang = "bash"
    )
}


