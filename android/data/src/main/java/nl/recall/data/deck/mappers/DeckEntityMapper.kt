package nl.recall.data.deck.mappers

import nl.recall.data.deck.models.DeckEntity
import nl.recall.domain.deck.model.Deck

object DeckEntityMapper {
    internal fun List<Deck>.toDomain(): List<DeckEntity>{
        return map {it.toDomain() }
    }

    fun Deck.toDomain(): DeckEntity{
        return DeckEntity(
            id = id,
            title = title,
            creationDate = creationDate,
            icon = icon,
            color = color
        )
    }
}