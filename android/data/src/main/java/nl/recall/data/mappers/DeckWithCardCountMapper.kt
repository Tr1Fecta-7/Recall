package nl.recall.data.mappers

import nl.recall.data.mappers.DeckMapper.toDomain
import nl.recall.data.models.DeckResult
import nl.recall.domain.deck.model.Deck

object DeckWithCardCountMapper {
    fun Map<DeckResult, Int>.toDomain(): Map<Deck, Int> {
        return this.mapKeys { keys -> keys.key.toDomain() }
    }
}