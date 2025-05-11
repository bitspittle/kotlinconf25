@file:Suppress("UnusedImport") // Useful to have imports ready to go when demonstrating code examples live

package dev.bitspittle.kotlinconf25.kobweb.pages.kobweb.fullstack

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import dev.bitspittle.kotlinconf25.kobweb.components.layouts.SlideTitle
import dev.bitspittle.kotlinconf25.kobweb.components.widgets.code.CodeBlock
import dev.bitspittle.kotlinconf25.kobweb.util.slides.StepTypes
import dev.bitspittle.kotlinconf25.kobweb.util.slides.step
import org.jetbrains.compose.web.css.percent

@InitRoute
fun initSetupPage(ctx: InitRouteContext) {
    ctx.data.add(SlideTitle("Setup"))
}

@Page
@Composable
@Layout(".components.layouts.TitledSlideLayout")
fun SetupPage() {
    fun Modifier.codeWidth() = this.width(70.percent)

    CodeBlock(
        """
        plugins {
            /*...*/    
        }
            
        kotlin {
            configAsKobwebApplication(
                "app",
            )
        
        
            sourceSets {
                jsMain.dependencies { /*...*/ }
            }
        }
        
        
        
        

        """.trimIndent(),
        preModifier = Modifier.step(StepTypes.OneAtATime, auto = true).codeWidth()
    )
    Box(Modifier.step(StepTypes.OneAtATime).codeWidth()) {
        CodeBlock(
            """
            plugins {
                /*...*/    
            }
            
            kotlin {
                configAsKobwebApplication(
                    "app",
                    includeServer = true
                )
                       
                sourceSets {
                    jsMain.dependencies { /*...*/ }
                    jvmMain.dependencies {
                        compileOnly(libs.kobweb.api)
                    }
                }
            }
            

            """.trimIndent(),
            highlightLines = "8,13-15",
            preModifier = Modifier.fillMaxWidth()
        )
    }
    Box(Modifier.step(StepTypes.OneAtATime).codeWidth()) {
        CodeBlock(
            """
            plugins {
                alias(libs.plugins.kotlin.serialization)
            }
            
            kotlin {
                configAsKobwebApplication(
                    "app",
                    includeServer = true
                )
            
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.kotlinx.serialization.json)
                    }
                    jsMain.dependencies { /*...*/ }
                    jvmMain.dependencies { /*...*/ }
                }
            }
            """.trimIndent(),
            highlightLines = "2,12-14",
            preModifier = Modifier.fillMaxWidth()
        )
    }
}

