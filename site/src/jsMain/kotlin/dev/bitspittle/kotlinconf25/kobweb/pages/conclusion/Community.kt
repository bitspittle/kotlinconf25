package dev.bitspittle.kotlinconf25.kobweb.pages.conclusion

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.media.Image
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initCommunityPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Community"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun CommunityPage() {
    Row(Modifier.fillMaxHeight().gap(4.cssRem), verticalAlignment = Alignment.CenterVertically) {
        Image("/assets/images/community/discord.png")
        Image("/assets/images/community/slack.png", scale = 0.5f)
    }
}
