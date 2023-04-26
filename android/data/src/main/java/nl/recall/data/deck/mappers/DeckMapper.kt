package nl.recall.data.deck.mappers

import nl.recall.data.deck.models.DeckEntity
import nl.recall.domain.deck.model.Deck

object DeckMapper {

    internal fun List<DeckEntity>.toDomain(): List<Deck> {
        return map { it.toDomain() }
    }

    fun DeckEntity.toDomain(): Deck {
        return Deck(
            id = id,
            title = title,
            creationDate = creationDate,
            icon = icon,
            color = color
        )
    }
}