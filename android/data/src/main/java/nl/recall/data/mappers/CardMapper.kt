package nl.recall.data.mappers

import nl.recall.data.models.CardEntity
import nl.recall.domain.deck.model.Card

object CardMapper {
    internal fun List<CardEntity>.toDomain(): List<Card> {
        return map { it.toDomain() }
    }

    fun CardEntity.toDomain(): Card {
        return Card(id, front, back, dueDate)
    }
}