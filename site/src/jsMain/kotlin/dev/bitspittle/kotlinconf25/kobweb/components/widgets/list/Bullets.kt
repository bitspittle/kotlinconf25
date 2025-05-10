package dev.bitspittle.kotlinconf25.kobweb.components.widgets.list

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

val BulletsLiStyle = CssStyle.base {
    Modifier.padding(topBottom = 0.1.cssRem)
}

val BulletsUlStyle = CssStyle.base { Modifier }

@Suppress("FunctionName") // Ape composable naming convention
class BulletsScope {
    private val children = mutableListOf<@Composable () -> Unit>()

    @Composable
    internal fun render() {
        children.forEach { it() }
    }

    fun Item(value: String, modifier: Modifier = Modifier) {
        RenderedItem(modifier) { Text(value) }
    }

    fun RenderedItem(modifier: Modifier = Modifier, render: @Composable () -> Unit) {
        children.add({
            Li(BulletsLiStyle.toModifier().then(modifier).toAttrs()) {
                render()
            }
        })
    }

    fun Item(value: String, modifier: Modifier = Modifier, children: BulletsScope.() -> Unit) {
        RenderedItem(modifier, { Text(value) }, children)
    }

    fun RenderedItem(modifier: Modifier = Modifier, render: @Composable () -> Unit, children: BulletsScope.() -> Unit) {
        val subscope = BulletsScope().apply(children)
        this.children.add {
            Li(BulletsLiStyle.toModifier().then(modifier).toAttrs()) {
                render()
                Ul(BulletsUlStyle.toAttrs()) { subscope.render() }
            }
        }
    }

}

@Composable
fun Bullets(modifier: Modifier = Modifier, content: BulletsScope.() -> Unit) {
    val scope = BulletsScope().apply(content)
    Ul(modifier.toAttrs()) { scope.render() }
}
