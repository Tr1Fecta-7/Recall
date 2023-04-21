package nl.recall.domain.models

data class DeckData(
    val title: String,
    val cards: Set<Card> = HashSet(),
    var backgroundColor: String = "#DE2910",
    var emoji: String = "\uD83E\uDDE0"
)
