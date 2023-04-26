package nl.recall.domain.deck.model

import java.util.Date

data class Deck (
    val id: Long,
    val title: String,
    val creationDate : Date,
    val icon : String,
    val color: String,
    )