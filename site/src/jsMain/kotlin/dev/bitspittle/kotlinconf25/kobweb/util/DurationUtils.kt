package dev.bitspittle.kotlinconf25.kobweb.util

import com.varabyte.kobweb.compose.css.CSSTimeNumericValue
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.s
import kotlin.time.Duration

fun Duration.toCssUnit(): CSSTimeNumericValue = this.inWholeMilliseconds.let {
    if (it > 1000) {
        (it.toFloat() / 1000).s
    } else {
        it.ms
    }
}