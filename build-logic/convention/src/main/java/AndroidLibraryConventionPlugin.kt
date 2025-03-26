import com.android.build.api.dsl.LibraryExtension
import com.sebascamayo.convention.ExtensionType
import com.sebascamayo.convention.configureBuildTypes
import com.sebascamayo.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

// PLUGIN DE LA CONFIGURACION DEL BUILD GRADLE DE ALGUNOS DE LOS MODULOS DE DATA Y OTROS
// SON PARA MODULOS POR DEBAJO DEL MODULO APP

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {

        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }

}