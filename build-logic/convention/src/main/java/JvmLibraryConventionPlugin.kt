import com.android.build.api.dsl.LibraryExtension
import com.sebascamayo.convention.ExtensionType
import com.sebascamayo.convention.configureAndroidCompose
import com.sebascamayo.convention.configureBuildTypes
import com.sebascamayo.convention.configureKotlinAndroid
import com.sebascamayo.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

// PLUGIN DE LA CONFIGURACION DEL BUILD GRADLE DE LA LIBRERIA DE JAVA

class JvmLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {

        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlinJvm()
        }
    }

}