import java.util.Properties

plugins {
    alias(libs.plugins.runique.android.feature.ui)
    alias(libs.plugins.mapsplatform.secrets.plugin)
    //alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sebascamayo.run.presentation"

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "local.properties")

    if(localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    buildTypes {
        debug {
            val mapsApiKey = localProperties.getProperty("MAPS_API_KEY")
            buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")

            manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
        }
        release {
            val mapsApiKey = localProperties.getProperty("MAPS_API_KEY")
            buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")

            manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
        }
    }
}

/*composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    //stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}*/

dependencies {

    implementation(libs.coil.compose)
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}