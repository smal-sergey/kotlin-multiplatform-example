plugins {
    kotlin("multiplatform")
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
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}