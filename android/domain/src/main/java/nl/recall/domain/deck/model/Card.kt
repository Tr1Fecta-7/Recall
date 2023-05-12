package nl.recall.domain.deck.model

import java.util.Date

data class Card(
    val id: Long,
    val front: String,
    val back: String,
    val dueDate: Date,
    val deckId: Long,
)
