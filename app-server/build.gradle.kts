plugins {
    kotlin("jvm")
}

val ktorVersion = project.property("ktor.version") as String
val logbackVersion = project.property("logback.version") as String

dependencies {
    api(project(":app-common"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    val copyWebClientTask =
        register<Copy>("copyWebClient") {
            dependsOn(named("processResources"))
            from(project(":app-client-web").tasks.named("browserDistribution"))
            into(file("$buildDir/resources/main/web"))
        }

    val fatJar =
        task<Jar>("fatJar") {
            dependsOn(copyWebClientTask)
            manifest {
                attributes["Main-Class"] = "com.smalser.server.ServerAppKt"
            }
            from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
            with(named("jar").get() as CopySpec)
        }

    "build" {
        dependsOn(fatJar)
    }
}