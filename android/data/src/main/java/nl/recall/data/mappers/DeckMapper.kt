package nl.recall.data.mappers

import nl.recall.data.models.DeckResult
import nl.recall.domain.deck.model.Deck

object DeckMapper {

    internal fun List<DeckResult>.toDomain(): List<Deck> {
        return map { it.toDomain() }
    }

    fun DeckResult.toDomain(): Deck {
        return Deck(id, title, creationDate, icon, backgroundColor)
    }
}