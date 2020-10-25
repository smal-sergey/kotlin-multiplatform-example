plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "com.smalser"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {

    jvm()
    js {
        browser()
    }

    sourceSets {

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("serialization"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}