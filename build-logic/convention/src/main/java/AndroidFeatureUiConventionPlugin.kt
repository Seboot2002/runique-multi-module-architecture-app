import com.android.build.api.dsl.LibraryExtension
import com.sebascamayo.convention.ExtensionType
import com.sebascamayo.convention.addUiLayerDependencies
import com.sebascamayo.convention.configureAndroidCompose
import com.sebascamayo.convention.configureBuildTypes
import com.sebascamayo.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

// PLUGIN DE LA CONFIGURACION DEL BUILD GRADLE DE ALGUNOS DE LOS MODULOS DE PRESENTATION
// SON PARA MODULOS POR DEBAJO DEL MODULO APP

class AndroidFeatureUiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        target.run {
            pluginManager.run {
                apply("runique.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }

}