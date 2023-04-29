package nl.recall.domain.repositories

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import java.util.Date

interface DeckRepository {
    suspend fun getDeckById(id: Long): DeckWithCards
    suspend fun getDeckWithCardCount(): Map<Deck, Int>
    suspend fun searchDeckWithCardCount(title: String): Map<Deck, Int>
    suspend fun saveDeck(title: String, creationDate: Date, icon: String, color: String): Boolean
}