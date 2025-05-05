package dev.bitspittle.kotlinconf25.kobweb.components.widgets.list

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Suppress("FunctionName") // Ape composable naming convention
class BulletsScope {
    private val children = mutableListOf<@Composable () -> Unit>()

    @Composable
    internal fun render() {
        children.forEach { it() }
    }

    fun Item(value: String, modifier: Modifier = Modifier) {
        children.add { Li(modifier.toAttrs()) { Text(value) } }
    }

    fun Item(value: String, modifier: Modifier = Modifier, content: BulletsScope.() -> Unit) {
        val subscope = BulletsScope().apply(content)
        children.add {
            Li(modifier.toAttrs()) {
                Text(value)
                Ul { subscope.render() }
            }
        }
    }
}

@Composable
fun Bullets(content: BulletsScope.() -> Unit) {
    val scope = BulletsScope().apply(content)
    Ul { scope.render() }
}
