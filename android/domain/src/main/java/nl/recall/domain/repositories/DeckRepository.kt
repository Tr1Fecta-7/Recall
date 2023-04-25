package nl.recall.domain.repositories

import nl.recall.domain.deck.model.DeckWithCards

interface DeckRepository {
    suspend fun getDeckById(id: Long): DeckWithCards?
}