package com.smalser.server

import com.smalser.common.Game
import com.smalser.common.hello_multiplatform
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import java.time.Duration
import java.util.*

@KtorExperimentalAPI
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    install(CORS) {
        method(HttpMethod.Get)
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
//        host("localhost")
        allowCredentials = true
        allowNonSimpleContentTypes = true
        maxAgeInSeconds = Duration.ofDays(1).seconds
    }

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
        get("/newGame") {
            val name: String = call.parameters.getOrFail("name")
            call.respond(Game(name, UUID.randomUUID().toString(), 0, 0))
        }
        get("/hello") {
            call.respond(hello_multiplatform())
        }
    }
}

@KtorExperimentalAPI
fun main() {
    embeddedServer(
        Netty,
        8081,
        watchPaths = listOf("app-server"),
        module = Application::module
    ).start()
}