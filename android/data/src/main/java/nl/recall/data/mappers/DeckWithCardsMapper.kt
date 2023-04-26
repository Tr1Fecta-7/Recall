package nl.recall.data.mappers

import nl.recall.data.mappers.CardMapper.toDomain
import nl.recall.data.mappers.DeckMapper.toDomain
import nl.recall.data.models.DeckWithCardsEntity
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