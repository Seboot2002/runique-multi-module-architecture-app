plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.sebascamayo.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}