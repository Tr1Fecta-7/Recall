package nl.recall.domain.deck.model

import java.util.Date

data class Card(
    var id : Long= 0,
    var front: String = "",
    var back : String = "",
    var dueDate : Date = Date()
)
