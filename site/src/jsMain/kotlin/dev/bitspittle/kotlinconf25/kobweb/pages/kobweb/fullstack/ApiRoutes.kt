@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.fullstack

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.MinWidth
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateY
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.MultiPartSlideLayoutScope
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Bullets
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.list.Folders
import dev.bitspittle.kotlinconf25.kobweb.style.Gaps
import dev.bitspittle.kotlinconf25.kobweb.util.slides.DefaultStepSpeed
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes.OneAtATime
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3

@InitRoute
fun initApiRoutesPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("API routes"))
}

private const val ShiftExampleDown = "api-route-shift-down"

@InitSilk
fun initShiftStepEffect(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase(".step.$ShiftExampleDown") {
            Modifier
                .translateY((-60).percent)
                .transition(Transition.of("translate", DefaultStepSpeed))
        }

        registerStyleBase(".step.$ShiftExampleDown.active") {
            Modifier.translateY(60.percent)
        }
    }
}

@Page
@Composable
@Layout(".components.layouts.MultiPartSlideLayout")
fun MultiPartSlideLayoutScope.ApiRoutesPage() {

    // Basic API route
    SlideSection {
        Column(Modifier.fillMaxSize().gap(Gaps.Normal), horizontalAlignment = Alignment.CenterHorizontally) {
            H3(Modifier.fontWeight(FontWeight.Normal).toAttrs()) {
                Folders {
                    Bullets {
                        Item("jvmMain") {
                            Item("api")
                        }
                    }
                }
            }
            Box(Modifier.step(StepTypes.FadeUp)) {
                CodeBlock(
                    """
                    // jvmMain/com/mysite/api/hello.kt
                    @Api
                    suspend fun hello(ctx: ApiContext) {
                        ctx.res.setBodyText("hello world")
                    }
                    """.trimIndent(),
                    highlightLines = "0|1|2|3"
                )
            }
        }
    }

    // Calling the API route from the frontend
    SlideSection {
        Column(Modifier.gap(Gaps.Tiny), horizontalAlignment = Alignment.CenterHorizontally) {
            fun Modifier.commonWidth() = fillMaxWidth()
            Box(Modifier.commonWidth()) {
                CodeBlock(
                    """
                    @Api
                    suspend fun hello(ctx: ApiContext) { /*...*/ }
                    """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth()
                )
            }

            Box(Modifier.step(StepTypes.FadeUp).commonWidth()) {
                CodeBlock(
                    """
                    @Page
                    @Composable
                    fun ExamplePage() {
                        var fetchedText by remember { mutableStateOf("") }
                        LaunchedEffect(Unit) {
                            fetchedText = 
                                window.api.get("hello")
                                    .decodeToString()
                        }
                    }
                    """.trimIndent(),
                    highlightLines = "0|7",
                    preModifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Request type, return code, request body, and data
    SlideSection {
        Box(Modifier.step(StepTypes.OneAtATime, auto = true)) {
            CodeBlock(
                """
                @Api
                suspend fun postMessage(ctx: ApiContext) {
                    if (ctx.req.method != HttpMethod.POST) return
    
                    // Get message from request
                    // Store message in a datastore
    
                    ctx.res.status = 200
                }


                """.trimIndent(),
                preModifier = Modifier.fillMaxWidth(),
                highlightLines = "0|3|8|5"
            )
        }
        Box(Modifier.step(StepTypes.OneAtATime)) {
            CodeBlock(
                """
                @Api
                suspend fun postMessage(ctx: ApiContext) {
                    if (ctx.req.method != HttpMethod.POST) return
    
                    val message = ctx.req.readBody()
                    // Store message in a datastore
    
                    ctx.res.status = 200
                }


                """.trimIndent(),
                preModifier = Modifier.fillMaxWidth(),
                highlightLines = "5|6"
            )
        }
        Box(Modifier.step(StepTypes.OneAtATime)) {
            CodeBlock(
                """
                @Api
                suspend fun postMessage(ctx: ApiContext) {
                    if (ctx.req.method != HttpMethod.POST) return
    
                    val message = ctx.req.readBody()
                    ctx.data.getValue<Messages>().add(message)
    
                    ctx.res.status = 200
                }


                """.trimIndent(),
                preModifier = Modifier.fillMaxWidth(),
                highlightLines = "6|8"
            )
        }
        Box(Modifier.step(StepTypes.OneAtATime)) {
            CodeBlock(
                """
                @Api
                suspend fun postMessage(ctx: ApiContext) {
                    if (ctx.req.method != HttpMethod.POST) return
    
                    val message = ctx.req.readBody()
                    ctx.data.getValue<MessageStore>().add(message)
    
                    ctx.res.setBodyText("OK")
                    // Side effect: sets res code to 200
                }
                """.trimIndent(),
                preModifier = Modifier.fillMaxWidth(),
                highlightLines = "8|0"
            )
        }
    }

    SlideSection {
        fun Modifier.codeWidth() = this.width(70.percent)

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(Modifier.step(ShiftExampleDown).codeWidth()) {
                CodeBlock(
                    """
                    @Api
                    suspend fun postMessage(ctx: ApiContext) {
                        ctx.data.getValue<Messages>()
                    }
                    """.trimIndent(),
                    preModifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Box(Modifier.step(StepTypes.FadeDown, auto = true).codeWidth()) {
            CodeBlock(
                """
                    @InitApi
                    fun initMessages(ctx: InitApiContext) {
                        ctx.data.add(Messages())
                    }
                    """.trimIndent(),
                preModifier = Modifier.fillMaxWidth().translateY((-60).percent),
                highlightLines = "0|1|2|3"
            )
        }
    }

    SlideSection {
        H3 {
            Link("/demo/guestbook")
        }
    }
}


