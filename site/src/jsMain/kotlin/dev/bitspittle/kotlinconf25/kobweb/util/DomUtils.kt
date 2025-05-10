package dev.bitspittle.kotlinconf25.kobweb.util

import kotlinx.browser.window
import org.w3c.dom.*

fun HTMLCollection.walkWhile(onEach: (Element) -> Boolean) {
    (0 until length)
        .mapNotNull { i: Int -> this[i] }
        .forEach { child ->
            if (!onEach(child)) return
            child.children.walkWhile(onEach)
        }
}

fun HTMLCollection.walk(onEach: (Element) -> Unit) {
    walkWhile { onEach(it); true }
}

fun NodeList.walkWhile(onEach: (Node) -> Boolean) {
    (0 until length)
        .mapNotNull { i: Int -> this[i] }
        .forEach { node ->
            if (!onEach(node)) return
            node.childNodes.walkWhile(onEach)
        }
}

fun NodeList.walk(onEach: (Node) -> Unit) {
    walkWhile { onEach(it); true }
}

val HTMLElement.ancestors: Sequence<HTMLElement>
    get() {
        var curr: HTMLElement? = this
        return sequence {
            while (curr != null) {
                yield(curr!!)
                curr = curr!!.parentElement as? HTMLElement
            }
        }
    }
