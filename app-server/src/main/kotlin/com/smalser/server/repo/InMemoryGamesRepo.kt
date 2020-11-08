package com.smalser.server.repo

import com.smalser.common.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryGamesRepo : GamesRepo {
    private val allGames = ConcurrentHashMap<String, Game>()

    override fun newGame(name: String): Game {
        val sessionId = UUID.randomUUID().toString()
        val game = Game(name, sessionId, 0, 0)
        allGames[sessionId] = game
        return game
    }

    override fun win(sessionId: String) {
        val game = allGames[sessionId]!!
        allGames[sessionId] = game.copy(won = game.won + 1)
    }

    override fun lose(sessionId: String) {
        val game = allGames[sessionId]!!
        allGames[sessionId] = game.copy(lost = game.lost + 1)
    }

    override fun getAllGames(): Collection<Game> = allGames.values
}