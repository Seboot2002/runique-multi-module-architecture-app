package com.sebascamayo.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs
                .findVersion("composeCompiler")
                .get()
                .toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
        }
    }

    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    // El siguiente codigo equivale a:
    /*
        composeCompiler {
            reportsDestination = layout.buildDirectory.dir("compose_compiler")
        }
    */
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            if (project.findProperty("nordvpn-app.enableComposeCompilerReports") == "true") {
                freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath,
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath
                    )
                )
            }
        }
    }
}