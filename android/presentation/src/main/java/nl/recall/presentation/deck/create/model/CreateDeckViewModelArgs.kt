package nl.recall.presentation.deck.create.model

import java.util.Date

class CreateDeckViewModelArgs(
    val id: Long,
    val title: String,
    val color: String,
    val creationDate: Date,
    val icon: String,
)
