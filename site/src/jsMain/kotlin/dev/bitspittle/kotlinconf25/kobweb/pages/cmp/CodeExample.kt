package dev.bitspittle.kotlinconf25.kobweb.pages.cmp

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock

@Page
@Composable
@Layout(".components.layouts.SlideLayout")
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
