import com.smalser.common.Game
import com.smalser.common.hello_multiplatform
import kotlinx.css.*
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
//    var visible: Boolean = true
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
        playerHand = Hand("My hand", mutableListOf(deck.random(), deck.random()))
        dealerHand = Hand("Dealer hand", mutableListOf(deck.random(), deck.random()))
        status = Status.IN_GAME
    }

    override fun RBuilder.render() {
        div {
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
                            playerHand.addCard(state.deck.random())

                            if (playerHand.score() == 21) {
                                status = Status.WON
                            }
                            if (playerHand.score() > 21) {
                                status = Status.LOST
                            }
                        }
                    }
                }
                button {
                    attrs.disabled = state.status != Status.IN_GAME
                    +"Stand"
                    attrs.onClickFunction = {
                        setState {
                            while (dealerHand.score() < 17) {
                                dealerHand.addCard(deck.random())
                            }
                            val dealerScore = dealerHand.score()
                            val playerScore = playerHand.score()
                            if (dealerScore > 21 || dealerScore < playerScore) {
                                status = Status.WON
                            } else if (dealerScore == playerScore) {
                                status = Status.PUSH
                            } else if (dealerScore > playerScore) {
                                status = Status.LOST
                            } else {
                                console.error("Unknown situation! Dealer score:$dealerScore, Player score: $playerScore")
                            }
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
        }
    }
}

fun RBuilder.table(handler: TableProps.() -> Unit): ReactElement {
    return child(TableComponent::class) {
        attrs(handler)
    }
}