import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledH1
import styled.styledH2

data class Card(val points: Int, val name: String)
data class Hand(val name: String, val cards: MutableList<Card>) {
    fun score() = cards.map { it.points }.sum()
    fun addCard(card: Card) {
        cards.add(card)
    }
}

enum class Status {
    WON,
    LOST,
    PUSH,
    IN_GAME
}

external interface GameState : RState {
    var deck: Deck
    var playerHand: Hand
    var dealerHand: Hand
    var status: Status
}

class App : RComponent<RProps, GameState>() {
    override fun GameState.init() {
        reinit()
    }

    private fun GameState.reinit() {
        console.log("Initializing state")
        deck = Deck()
        playerHand = Hand("My hand", mutableListOf(deck.random(), deck.random()))
        dealerHand = Hand("Dealer hand", mutableListOf(deck.random(), deck.random()))
        status = Status.IN_GAME
    }

    override fun RBuilder.render() {
        div {
            styledH1 {
                css.textAlign = TextAlign.center
                +"Welcome to Blackjack game!"
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