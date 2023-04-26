package nl.recall.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithCardsEntity(
    @Embedded val deckEntity: DeckEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "deck_id"
    )
    val cardEntity: List<CardEntity>?
)