import com.android.build.api.dsl.LibraryExtension
import com.sebascamayo.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

// PLUGIN DE LA CONFIGURACION DEL BUILD GRADLE DE ALGUNOS DE LOS MODULOS DE PRESENTATION
// SON PARA MODULOS POR DEBAJO DEL MODULO APP

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {

        target.run {
            pluginManager.run {
                apply("runique.android.library")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }

}