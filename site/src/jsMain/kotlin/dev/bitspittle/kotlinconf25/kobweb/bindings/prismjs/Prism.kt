package dev.bitspittle.kotlinconf25.kobweb.bindings.prismjs

import org.w3c.dom.Node

// See kobweb config in build.gradle.kts which sets up Prism
@Suppress("unused")
external class Prism {
    companion object {
        fun highlightAll()
        fun highlightAllUnder(container: Node)
        fun highlightElement(element: Node)
    }
}