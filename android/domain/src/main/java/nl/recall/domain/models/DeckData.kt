package nl.recall.domain.models

data class DeckData(
    val title: String,
    val cards: Set<Card> = HashSet(),
    val backgroundColor: String = "#DE2910",
    val emoji: String = "\uD83E\uDDE0"
)
