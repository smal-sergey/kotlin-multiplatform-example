plugins {
    id("org.jetbrains.kotlin.js") version "1.3.72"
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin.target.browser { }