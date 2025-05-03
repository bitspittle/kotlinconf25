import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "dev.bitspittle.kotlinconf25.kobweb"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        globals.put("slides",
            listOf(
                "",
                "showcase",
                "cmp/html-vs-wasm1",
                "cmp/code-example",
            ).joinToString()
        )

        index {
            description.set("KotlinConf 2025 - Build Websites in Kotlin & Compose HTML with Kobweb")
            faviconPath.set("favicon.svg")

            head.add {
                link {
                    rel = "preconnect"
                    href = "https://fonts.googleapis.com"
                }
                link {
                    rel = "preconnect"
                    href = "https://fonts.gstatic.com"
                    attributes["crossorigin"] = ""
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Roboto+Mono:ital,wght@0,100..700;1,100..700&display=swap"
                }

                link {
                    rel = "stylesheet"
                    href = "/prism/prism.css"
                }
                script {
                    src = "/prism/prism.js"
                }
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("kobweb", includeServer = true)

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here
//        }
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            implementation(project(":worker"))
        }
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }
    }
}
