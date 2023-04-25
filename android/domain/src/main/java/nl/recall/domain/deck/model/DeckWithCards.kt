package nl.recall.domain.deck.model

data class DeckWithCards(
    val deck: Deck = Deck(),
    val cards: List<Card> = listOf()
)
