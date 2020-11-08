package com.smalser.server

import com.smalser.common.Game
import com.smalser.common.Statistics
import com.smalser.server.repo.GamesRepo
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

@KtorExperimentalAPI
fun Routing.games(repo: GamesRepo) {
    route("/game") {
        get("/new") {
            val name: String = call.parameters.getOrFail("name")
            call.respond(repo.newGame(name))
        }
        post("/win") {
            val sessionId: String = call.parameters.getOrFail("sessionId")
            repo.win(sessionId)
            call.respond("Posted!")
        }
        post("/lose") {
            val sessionId: String = call.parameters.getOrFail("sessionId")
            repo.lose(sessionId)
            call.respond("Posted!")
        }
        get("/statistics") {
            val games: Collection<Game> = repo.getAllGames()
            val sortedGames: List<Game> = games.filter { it.gamesPlayed > 0 }.sortedByDescending { game -> game.rating }
            call.respond(Statistics(sortedGames))
        }
    }
}