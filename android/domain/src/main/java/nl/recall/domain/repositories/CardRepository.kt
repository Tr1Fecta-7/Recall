package nl.recall.domain.repositories

import nl.recall.domain.deck.model.Card


interface CardRepository {
    suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<Card>
}