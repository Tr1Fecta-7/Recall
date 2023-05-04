package nl.recall.domain.repositories

import nl.recall.domain.deck.model.Card
import java.util.Date


interface CardRepository {
    suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<Card>

    suspend fun saveCard(id: Long, front: String, back: String, dueDate: Date, deckId: Long): Boolean
}