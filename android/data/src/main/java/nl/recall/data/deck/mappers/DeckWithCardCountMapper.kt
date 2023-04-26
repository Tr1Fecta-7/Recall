package nl.recall.data.deck.mappers

import nl.recall.data.deck.mappers.DeckMapper.toDomain
import nl.recall.data.deck.models.DeckEntity
import nl.recall.domain.deck.model.Deck

object DeckWithCardCountMapper {
    fun Map<DeckEntity, Int>.toDomain(): Map<Deck, Int> {
        return this.mapKeys { keys -> keys.key.toDomain() }
    }
}