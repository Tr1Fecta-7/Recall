package nl.recall.data.mappers

import nl.recall.data.mappers.DeckMapper.toDomain
import nl.recall.data.models.DeckEntity
import nl.recall.domain.deck.model.Deck

object DeckWithCardCountMapper {
    fun Map<DeckEntity, Int>.toDomain(): Map<Deck, Int> {
        return this.mapKeys { keys -> keys.key.toDomain() }
    }
}