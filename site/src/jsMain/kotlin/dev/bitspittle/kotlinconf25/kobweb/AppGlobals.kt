package dev.bitspittle.kotlinconf25.kobweb

import com.varabyte.kobweb.core.AppGlobals
import com.varabyte.kobweb.navigation.BasePath

class Slides(private val slideList: List<String>) {
    private val slideIndices = slideList.mapIndexed { i, v -> v to i }.toMap()

    val size get() = slideList.size

    fun indexOf(path: String) = slideIndices[path] ?: -1

    fun contains(path: String): Boolean {
        return slideIndices.containsKey(path)
    }

    fun prev(path: String): String? {
        return slideIndices[path]?.let { slideList.getOrNull(it - 1) }
    }

    fun next(path: String): String? {
        return slideIndices[path]?.let { slideList.getOrNull(it + 1) }
    }
}

private val _slides: Slides by lazy {
    Slides(AppGlobals["slides"]?.split(",")?.map { BasePath.prependTo("/" + it.trim()) } ?: listOf())
}

val AppGlobals.slides: Slides get() { return _slides }