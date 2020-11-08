package com.smalser.common

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val name: String,
    var sessionId: String = "",
    var lost: Int = 0,
    var won: Int = 0
) {
    val gamesPlayed = won + lost

    //dealers rating always 1_000
    //https://en.wikipedia.org/wiki/Elo_rating_system#Performance_rating
    val rating = if (gamesPlayed == 0) 0.0 else (1000.0 * gamesPlayed + 400 * (won - lost)) / gamesPlayed
}

//by some reason Ktor cannot serialize JSON with top-level list using kotlinx.serialization
@Serializable
data class Statistics(val games: List<Game>)