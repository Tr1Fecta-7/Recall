package nl.recall.data.mappers

import nl.recall.data.models.DeckEntity
import nl.recall.domain.deck.model.Deck

object DeckMapper {

    internal fun List<DeckEntity>.toDomain(): List<Deck> {
        return map { it.toDomain() }
    }

    fun DeckEntity.toDomain(): Deck {
        return Deck(id, title, creationDate, icon, backgroundColor)
    }
}