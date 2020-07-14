package com.smalser.common

expect class Platform() {
    val name: String
}

fun hello_multiplatform() = "Hello from ${Platform().name}!"