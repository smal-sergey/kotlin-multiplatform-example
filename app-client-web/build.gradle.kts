plugins {
    idea
    kotlin("js")
    kotlin("plugin.serialization")
}

val kotlinxSerializationVersion = project.property("kotlinx.serialization.version") as String
val kotlinxCoroutinesVersion = project.property("kotlinx.coroutines.version") as String
val kotlinWrappersSuffix = project.property("kotlin.wrappers.suffix") as String
val reactVersion = "17.0.0"
val kotlinReactVersion = "$reactVersion-pre.126-kotlin-$kotlinWrappersSuffix"
val styledVersion = "5.2.0"
val kotlinStyledVersion = "5.2.0-pre.126-kotlin-$kotlinWrappersSuffix"

dependencies {

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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$kotlinxCoroutinesVersion")

    implementation(kotlin("serialization"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

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