plugins {
    val kotlinVersion = "1.4.10"

    kotlin("js") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    kotlin("multiplatform") version kotlinVersion apply false
}

allprojects {
    group = "com.smalser"
    version = "1.0-SNAPSHOT"

    repositories {
        maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
        mavenCentral()
        jcenter()
    }
}