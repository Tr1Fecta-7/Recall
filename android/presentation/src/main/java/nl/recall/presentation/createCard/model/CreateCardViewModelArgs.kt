package nl.recall.presentation.createCard.model

import java.util.Date

class CreateCardViewModelArgs (
    val id: Long,
    val front: String,
    val back: String,
    val dueDate: Date,
    val deckId: Long,
)