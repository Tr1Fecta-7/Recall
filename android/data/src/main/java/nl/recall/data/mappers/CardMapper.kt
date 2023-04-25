package nl.recall.data.mappers

import nl.recall.data.models.CardResult
import nl.recall.domain.deck.model.Card

object CardMapper {
    internal fun List<CardResult>.toDomain(): List<Card> {
        return map { it.toDomain() }
    }

    fun CardResult.toDomain(): Card {
        return Card(id, front, back, dueDate)
    }
}