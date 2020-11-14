plugins {
    idea
    kotlin("js")
    kotlin("plugin.serialization")
}

//val ktorVersion by extra("1.4.0")
dependencies {
    val reactVersion = "17.0.0"
    val kotlinReactVersion = "17.0.0-pre.126-kotlin-1.4.10"
    val styledVersion = "5.2.0"
    val kotlinStyledVersion = "5.2.0-pre.126-kotlin-1.4.10"

    implementation(project(":app-common"))

    implementation(kotlin("stdlib-js"))
    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains:kotlin-react-dom:$kotlinReactVersion")
    implementation("org.jetbrains:kotlin-react:$kotlinReactVersion")
    implementation(npm("react", reactVersion))
    implementation(npm("react-dom", reactVersion))

    //Kotlin Styled (chapter 3)
    implementation("org.jetbrains:kotlin-styled:$kotlinStyledVersion")
    implementation(npm("styled-components", version = styledVersion))
    implementation(npm("inline-style-prefixer", version = "6.0.0"))

    //Video Player (chapter 7)
    implementation(npm("react-player", version = "2.6.2"))

    //Share Buttons (chapter 7)
    implementation(npm("react-share", version = "4.3.1"))

    //Coroutines (chapter 8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.7")

//may be migrate to Ktor JS client in future
//    implementation("io.ktor:ktor-client-js:$ktorVersion")
//    implementation("io.ktor:ktor-client-json:$ktorVersion")
//    implementation("io.ktor:ktor-client-json-js:$ktorVersion")
//    implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")
    implementation(kotlin("serialization"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")

    testImplementation(kotlin("test-js"))
}

kotlin {
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
}

idea {
    module {
        isDownloadJavadoc = true
    }
}