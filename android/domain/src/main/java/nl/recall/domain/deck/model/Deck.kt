package nl.recall.domain.deck.model

import java.util.Date

data class Deck (
    var id: Long = 0,
    var title: String = "",
    var creationDate : Date = Date(),
    var icon : String = "",
    var background_color: String = "",
    )