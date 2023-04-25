package nl.recall.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class DeckWithCardsResult(
    @Embedded val deckResult: DeckResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "deck_id"
    )
    val cardResult: List<CardResult>
)