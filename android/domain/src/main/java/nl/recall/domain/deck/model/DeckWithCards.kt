package nl.recall.domain.deck.model

data class DeckWithCards(
    val deck: Deck,
    val cards: List<Card>
)
