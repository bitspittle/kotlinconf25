package dev.bitspittle.kotlinconf25.kobweb.pages.cmp

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.TitledSlideLayoutData
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock

@InitRoute
fun initCodeExamplePage(ctx: InitRouteContext) {
    ctx.data.add(TitledSlideLayoutData("Compose Code Example"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun CodeExamplePage() {
    CodeBlock(
        """
                    Column(
                        Modifier
                            .padding(padding)
                            .fillMaxWidth()
                    ) {
                        Row { /*...*/ }
                        Spacer(Modifier.size(padding))
                        Button(
                           onClick = { /*...*/ },
                           Modifier.fillMaxWidth()
                        ) {
                           Text("Accept")
                        }
                    }
                """.trimIndent()
    )
}
