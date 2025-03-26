package com.sebascamayo.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

// VARIABLE QUE OBTIENE EL CATALOGO DE VERSIONES MEDIATE UNA EXTENSION

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")