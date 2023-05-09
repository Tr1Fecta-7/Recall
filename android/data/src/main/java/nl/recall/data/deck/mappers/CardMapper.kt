package nl.recall.data.deck.mappers

import nl.recall.data.deck.models.CardEntity
import nl.recall.domain.deck.model.Card

object CardMapper {
    internal fun List<CardEntity>.toDomain(): List<Card> {
        return map { it.toDomain() }
    }

    fun CardEntity.toDomain(): Card {
        return Card(id = id, front = front, back = back, dueDate = dueDate, deckId = deckId)
    }
}