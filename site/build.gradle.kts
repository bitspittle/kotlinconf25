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
                "caution",
                "html/header",
                "html/html-to-kotlin",
                "html/wasm-vs-html",
                "kobweb/basics/header",
                "kobweb/basics/why",
                "kobweb/basics/architecture",
                "kobweb/basics/install",
                "kobweb/basics/create",
                "kobweb/basics/run",
                "kobweb/basics/export",
                "kobweb/basics/organization",
                "kobweb/basics/html-to-kobweb",
                "kobweb/basics/css-style",
                "kobweb/basics/routing",
                "kobweb/basics/page-exercise",
                "kobweb/markdown/header",
                "kobweb/markdown/setup",
                "kobweb/markdown/frontmatter",
                "kobweb/markdown/code",
                "kobweb/markdown/markdown-slide",
                "kobweb/fullstack/header",
                "kobweb/fullstack/fullstack-vs-static",
                "kobweb/fullstack/setup",
                "kobweb/fullstack/api-routes",
                "conclusion/header",
                "conclusion/recap",
                "conclusion/docs",
                "conclusion/community",
                "thanks",
            ).joinToString()
        )

        index {
            description.set("KotlinConf 2025 - Build Websites in Kotlin & Compose HTML with Kobweb")
            faviconPath.set("favicon.svg")

            head.add {
                link {
                    rel = "stylesheet"
                    href = "/fonts/css2.css"
                }
                link {
                    rel = "stylesheet"
                    href = "/prism/prism.css"
                }
                script {
                    src = "/prism/prism.js"
                }
            }

            interceptUrls {
                replace("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css", "/font-awesome/css/all.min.css")
                enableSelfHosting()
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("kobweb", includeServer = true)

    sourceSets {
        jsMain.dependencies {
            implementation(libs.jetbrains.annotations)

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
