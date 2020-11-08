package com.smalser.server.repo

import com.smalser.common.Game

interface GamesRepo {
    fun newGame(name: String): Game
    fun win(sessionId: String)
    fun lose(sessionId: String)
    fun getAllGames(): Collection<Game>
}
