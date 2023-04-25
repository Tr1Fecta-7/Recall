package nl.recall.data.mappers

import nl.recall.data.mappers.CardMapper.toDomain
import nl.recall.data.mappers.DeckMapper.toDomain
import nl.recall.data.models.DeckWithCardsResult
import nl.recall.domain.deck.model.DeckWithCards

object DeckWithCardsMapper {
    fun DeckWithCardsResult.toDomain(): DeckWithCards? {
        return if (deckResult != null) {
            DeckWithCards(deckResult.toDomain(), cardResult.orEmpty().toDomain())
        } else {
            null
        }
    }
}