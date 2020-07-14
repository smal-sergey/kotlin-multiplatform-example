plugins {
    val kotlinVersion = "1.3.72"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

val ktor_version by extra("1.3.2")
dependencies {
    api(project(":app-common"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("serialization"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}