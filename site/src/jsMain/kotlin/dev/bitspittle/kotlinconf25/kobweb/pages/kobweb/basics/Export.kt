package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.basics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Video
import org.jetbrains.compose.web.css.percent

@InitRoute
fun initExportPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Exporting a project"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun ExportPage() {
    Video("/assets/cli/kobweb-export.mp4", Modifier.fillMaxSize(90.percent))
}
