data class Card(val points: Int, val name: String)
data class Hand(val name: String, val cards: MutableList<Card>) {
    fun score(): Int {
        val currentPoints = cards.map { it.points }.toMutableList()
        //should be a loop to be able replace several Aces
        while (currentPoints.sum() > 21 && currentPoints.contains(11)) {
            //replacing Ace 11 points to 1
            currentPoints.remove(11)
            currentPoints.add(1)
        }
        return currentPoints.sum()
    }

    fun addCard(card: Card) {
        cards.add(card)
    }
}

enum class Suit {
    SPADES,
    HEARTS,
    DIAMONDS,
    CLUBS;

    override fun toString(): String {
        val upperCaseName = super.toString()
        return upperCaseName.first() + upperCaseName.substring(1).toLowerCase()
    }
}

class Deck {
    private val deck: MutableList<Card> = newDeck()

    fun getRandomCard(): Card {
        val random = deck.random()
        deck.remove(random)
        return random
    }

    private fun newDeck() = mutableListOf<Card>()
        .asSequence()
        .plus(generate(Suit.SPADES))
        .plus(generate(Suit.HEARTS))
        .plus(generate(Suit.DIAMONDS))
        .plus(generate(Suit.CLUBS))
        .toMutableList()

    private fun generate(suit: Suit): List<Card> {
        val cards = mutableListOf<Card>()
        for (value in 2..10) {
            cards.add(Card(value, "$value of $suit"))
        }
        cards.add(Card(10, "Jack of $suit"))
        cards.add(Card(10, "Queen of $suit"))
        cards.add(Card(10, "King of $suit"))
        cards.add(Card(11, "Ace of $suit"))

        return cards
    }
}