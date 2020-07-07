package com.smalser.server

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.Serializable
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

@UnstableDefault
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
    }
}

@Serializable
data class Payload(val message: String)

@UnstableDefault
fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("ServerAppKt"), module = Application::module).start()
}