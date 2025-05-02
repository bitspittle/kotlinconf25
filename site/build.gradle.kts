import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

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
        index {
            description.set("KotlinConf 2025 - Build Websites in Kotlin & Compose HTML with Kobweb")
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
