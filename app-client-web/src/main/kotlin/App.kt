import com.smalser.common.Game
import com.smalser.common.Statistics
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.Display
import kotlinx.css.TextAlign
import kotlinx.css.display
import kotlinx.css.textAlign
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.events.Event
import org.w3c.fetch.RequestInit
import react.*
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledH1

interface GameState : RState {
    var authorized: Boolean
    var game: Game
    var statistics: Statistics
}

class App : RComponent<RProps, GameState>() {
    private val mainScope = MainScope()

    override fun GameState.init() {
        authorized = false
        game = Game("dummy")
        statistics = Statistics(emptyList())
    }

    override fun RBuilder.render() {
        div {
            styledH1 {
                css.textAlign = TextAlign.center
                if (state.authorized) {
                    +"Welcome to Blackjack game, ${state.game.name}!"
                } else {
                    +"Welcome to Blackjack game!"
                }
            }

            if (state.authorized) {
                styledDiv {
                    css {
                        display = Display.flex
                    }
                    gameTable {
                        onStatusChange = { newStatus ->
                            if (newStatus == Status.WON) {
                                mainScope.launch {
                                    console.info("Posting win")
                                    postWin(state.game.sessionId)
                                    updateStatistics()
                                }
                            } else if (newStatus == Status.LOST) {
                                mainScope.launch {
                                    console.info("Posting lose")
                                    postLose(state.game.sessionId)
                                    updateStatistics()
                                }
                            }
                        }
                    }
                    statistics {
                        statistics = state.statistics
                        currentGame = state.game
                    }
                }
            } else {
                login {
                    onSubmitFunction = { event, value -> loginSubmitted(event, value) }
                }
            }
        }
    }

    private fun loginSubmitted(event: Event, value: String) {
        console.info("Submitting: $value")
        event.preventDefault()

        val mainScope = MainScope()
        mainScope.launch {
            val newGame = newGame(value)
            console.info("Fetched game: ", newGame)
            updateStatistics()

            setState {
                console.info("Setting newGame to state")
                game = newGame
                authorized = true
            }
        }
    }

    private suspend fun newGame(name: String): Game =
        window.fetch("http://localhost:8081/game/new?name=$name")
            .await()
            .json()
            .await()
            .unsafeCast<Game>()

    private suspend fun updateStatistics() {
        console.info("Fetching statistics")
        val newStatistics = fetchStatistics()

        setState {
            statistics = newStatistics
        }
    }

    private suspend fun fetchStatistics(): Statistics {
        val jsonStr: String = window.fetch("http://localhost:8081/game/statistics")
            .await()
            .text()
            .await()

        return Json.decodeFromString(jsonStr)
    }

    private suspend fun postWin(sessionId: String) {
        window.fetch("http://localhost:8081/game/win?sessionId=$sessionId", requestInit(method = "POST"))
            .await()
    }

    private suspend fun postLose(sessionId: String) {
        window.fetch("http://localhost:8081/game/lose?sessionId=$sessionId", requestInit(method = "POST"))
            .await()
    }

    private fun requestInit(method: String = "GET", body: Any? = null) =
        if (body == null) {
            RequestInit(method = method)
        } else {
            RequestInit(method = method, headers = "{ 'Content-Type': 'application/json' }", body = body)
        }


//    Looks like Ktor JS Client is not ready for usage... too many strange exceptions during deserialization or rest calls
//    object GameApi {
//        private const val baseUrl = "http://localhost:8081/game"
//        private val client = HttpClient(Js) {
//            install(JsonFeature) {
////                serializer = defaultSerializer()
//                serializer = KotlinxSerializer()
//            }
//        }
//
//        suspend fun newGame(name: String): Game {
//            console.log("Doing call newGame")
//            return client.get("$baseUrl/new?name=$name")
//        }
//
//        suspend fun fetchStatistics(): Statistics {
//            console.log("Doing call fetchStatistics")
//            return client.get("$baseUrl/statistics")
//        }
//    }

}