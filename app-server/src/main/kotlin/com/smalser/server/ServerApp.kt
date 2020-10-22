package com.smalser.server

import com.smalser.common.hello_multiplatform
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
            }
        )
    }
    install(Routing) {
        get("/") {
            call.respond("My test message")
        }
        get("/test") {
            call.respond(Payload("Hi there!"))
        }
        get("/hello") {
            call.respond(hello_multiplatform())
        }
    }
}

@Serializable
data class Payload(val message: String)

fun main() {
    embeddedServer(
        Netty,
        8080,
        watchPaths = listOf("app-server"),
        module = Application::module
    ).start()
}