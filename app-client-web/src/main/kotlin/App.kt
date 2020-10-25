import com.smalser.common.Game
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.TextAlign
import kotlinx.css.textAlign
import org.w3c.dom.events.Event
import react.*
import react.dom.div
import styled.styledH1

interface GameState : RState {
    var authorized: Boolean
    var game: Game
}

class App : RComponent<RProps, GameState>() {
    override fun GameState.init() {
        authorized = false
        game = Game("dummy")
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
                table { }
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
            val newGame = fetchGame(value)
            console.info("Fetched game: ", newGame)

            setState {
                console.info("Setting newGame to state")
                game = newGame
                authorized = true
            }
        }
    }

    private suspend fun fetchGame(name: String): Game =
        window.fetch("http://localhost:8081/newGame?name=$name")
            .await()
            .json()
            .await()
            .unsafeCast<Game>()
}