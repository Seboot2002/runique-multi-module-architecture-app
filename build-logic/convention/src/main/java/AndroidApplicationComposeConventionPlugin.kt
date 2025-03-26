import com.android.build.api.dsl.ApplicationExtension
import com.sebascamayo.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

// PLUGIN DE CONFIGURACION GENERAL DE COMPOSE DEL BUILD GRADLE DE LA APP

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {

        target.run {
            pluginManager.apply("runique.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}