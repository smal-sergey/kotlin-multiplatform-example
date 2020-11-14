import com.smalser.common.Game
import com.smalser.common.hello_multiplatform
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledH2

enum class Status {
    WON,
    LOST,
    PUSH,
    IN_GAME
}

interface TableProps : RProps {
    var onStatusChange: (newStatus: Status) -> Unit
}

interface TableState : RState {
    var deck: Deck
    var playerHand: Hand
    var dealerHand: Hand
    var status: Status
}

class TableComponent : RComponent<TableProps, TableState>() {
    override fun TableState.init() {
        console.log("!!!!!!!!!!!!!! " + hello_multiplatform())
        console.log("!!!!!!!!!!!!!! " + Game("Common data class"))
        reinit()
    }

    private fun TableState.reinit() {
        console.log("Initializing state")
        deck = Deck()
        playerHand = Hand("My hand", mutableListOf(deck.getRandomCard(), deck.getRandomCard()))
        dealerHand = Hand("Dealer hand", mutableListOf(deck.getRandomCard(), deck.getRandomCard()))
        status = Status.IN_GAME
    }

    override fun RBuilder.render() {
        console.info("Rendering game table for current status: ${state.status}")
        div {
            styledDiv {
                css {
                    display = Display.flex
                }
                hand {
                    hand = state.playerHand
                    visible = true
                }
                hand {
                    hand = state.dealerHand
                    visible = (state.status != Status.IN_GAME)
                }
            }

            div {
                button {
                    +"Hit"
                    attrs.disabled = state.status != Status.IN_GAME
                    attrs.onClickFunction = {
                        setState {
                            playerHand.addCard(state.deck.getRandomCard())

                            val score = playerHand.score()
                            val newStatus = when {
                                (score == 21) -> Status.WON
                                (score > 21) -> Status.LOST
                                else -> status
                            }
                            if (newStatus != status) {
                                status = newStatus
                                props.onStatusChange(newStatus)
                            }
                            console.info("playerHand.score()=$score newStatus=$newStatus")
                        }
                    }
                }
                button {
                    attrs.disabled = state.status != Status.IN_GAME
                    +"Stand"
                    attrs.onClickFunction = {
                        setState {
                            while (dealerHand.score() < 17) {
                                dealerHand.addCard(deck.getRandomCard())
                            }
                            val dealerScore = dealerHand.score()
                            val playerScore = playerHand.score()
                            val newStatus = when {
                                (dealerScore > 21 || dealerScore < playerScore) -> Status.WON
                                (dealerScore == playerScore) -> Status.PUSH
                                (dealerScore > playerScore) -> Status.LOST
                                else -> {
                                    console.error("Unknown situation! Dealer score:$dealerScore, Player score: $playerScore")
                                    status
                                }
                            }

                            if (newStatus != status) {
                                status = newStatus
                                props.onStatusChange(newStatus)
                            }
                            console.info("playerScore=$playerScore, dealerScore=$dealerScore, newStatus=$newStatus")
                        }
                    }
                }
                button {
                    +"Try again!"
                    attrs.onClickFunction = {
                        setState {
                            reinit()
                        }
                    }
                }
            }

            div {
                styledH2 {
                    when (state.status) {
                        Status.LOST -> {
                            +"You lost!"
                            css.color = Color.red
                        }
                        Status.WON -> {
                            +"You won!"
                            css.color = Color.green
                        }
                        Status.PUSH -> {
                            +"Push, no one wins!"
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.gameTable(handler: TableProps.() -> Unit): ReactElement {
    return child(TableComponent::class) {
        attrs(handler)
    }
}