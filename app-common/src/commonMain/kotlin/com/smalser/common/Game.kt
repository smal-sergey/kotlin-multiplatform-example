package com.smalser.common

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val name: String,
    var sessionId: String = "",
    var lost: Int = 0,
    var won: Int = 0
)