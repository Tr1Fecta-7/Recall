package nl.recall.data.deck.mappers

import nl.recall.data.deck.mappers.CardMapper.toDomain
import nl.recall.data.deck.mappers.DeckMapper.toDomain
import nl.recall.data.deck.models.DeckWithCardsEntity
import nl.recall.domain.deck.model.DeckWithCards

object DeckWithCardsMapper {
    fun DeckWithCardsEntity.toDomain(): DeckWithCards? {
        return if (deckEntity != null) {
            DeckWithCards(deckEntity.toDomain(), cardEntity.orEmpty().toDomain())
        } else {
            null
        }
    }
}