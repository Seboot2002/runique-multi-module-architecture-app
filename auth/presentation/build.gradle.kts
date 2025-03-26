plugins {
    alias(libs.plugins.runique.android.feature.ui)
    //alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sebascamayo.auth.presentation"
}

/*composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    //stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}*/

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}