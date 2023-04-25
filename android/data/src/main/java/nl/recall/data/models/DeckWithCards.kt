package nl.recall.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithCards(
    @Embedded val deck: Deck,
    @Relation(
        parentColumn = "id",
        entityColumn = "deck_id"
    )
    val cards: List<Card>
)