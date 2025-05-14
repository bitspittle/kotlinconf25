package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaScrewdriverWrench
import com.varabyte.kobweb.silk.components.icons.fa.FaServer
import com.varabyte.kobweb.silk.components.icons.fa.FaTerminal
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.text.Text
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.style.SiteColors
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initArchitecturePage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle {
        SiteColors.KobwebBlue.Text("Kobweb")
        Text(" Architecture")
    })
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun ArchitecturePage() {
    @Composable
    fun IconText(icon: @Composable () -> Unit, text: String) {
        Column(Modifier.padding(Gaps.Large).gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            icon()
            SiteColors.OffWhite.Text(text)
        }
    }

    H4 {
        SimpleGrid(numColumns(2)) {
            IconText({ FaTerminal() }, "CLI")
            IconText({ FaScrewdriverWrench() }, "Gradle plugins | KSP")
            IconText({ FaServer() }, "Server")
            IconText({ FaCode() }, "Library")
        }
    }
}


