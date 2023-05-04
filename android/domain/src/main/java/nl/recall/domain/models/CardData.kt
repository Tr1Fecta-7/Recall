package nl.recall.domain.models

import java.util.Date

data class CardData(
    val id: Int,
    val deckId: Int,
    val front: String,
    val back: String,
    val due_date: Date
)
