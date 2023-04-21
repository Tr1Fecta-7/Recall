package nl.recall.domain.models

data class DeckData(
    val id: Int,
    val title: String,
    val cards: Set<Card>,
    val backgroundColor: String,
    val emoji: String
)
