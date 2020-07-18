class Deck() {
    private val deck: MutableList<Card> = newDeck()

    fun random(): Card {
        val random = deck.random()
        deck.remove(random)
        return random
    }

    private fun newDeck() = mutableListOf<Card>()
            .asSequence()
            .plus(generate("Spades"))
            .plus(generate("Hearts"))
            .plus(generate("Diamonds"))
            .plus(generate("Clubs"))
            .toMutableList()

    private fun generate(type: String): List<Card> {
        val cards = mutableListOf<Card>()
        for (value in 2..10) {
            cards.add(Card(value, "$value of $type"))
        }
        cards.add(Card(10, "Jack of $type"))
        cards.add(Card(10, "Queen of $type"))
        cards.add(Card(10, "King of $type"))
        cards.add(Card(11, "Ace of $type")) //todo update to soft hands in future

        return cards
    }
}