package com.smalser.server

import com.smalser.server.repo.InMemoryGamesRepo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import java.time.Duration

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
                encodeDefaults = true
            }
        )
    }
    install(Routing) {
        games(InMemoryGamesRepo())
        static()
    }
}

@KtorExperimentalAPI
fun main() {
    embeddedServer(
        Netty,
        8081,
        //doesn't work for JARs, probably config file should be used instead: https://ktor.io/docs/autoreload.html#configuration-file
//        watchPaths = listOf("app-server"),
        module = Application::module
    ).start()
}