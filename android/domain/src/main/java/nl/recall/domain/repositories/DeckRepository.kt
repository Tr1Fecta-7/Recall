package nl.recall.domain.repositories

import kotlinx.coroutines.flow.Flow
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import java.util.Date

interface DeckRepository {
    suspend fun observeDeckById(id: Long): Flow<DeckWithCards?>
    suspend fun getDeckById(id: Long): DeckWithCards
    suspend fun getDeckWithCardCount(): Map<Deck, Int>
    suspend fun observeDecksWithCardCount(): Flow<Map<Deck, Int>>
    suspend fun searchDeckWithCardCount(title: String): Map<Deck, Int>
    suspend fun saveDeck(title: String, creationDate: Date, icon: String, color: String): Boolean
    suspend fun saveDeckAndGetId(title: String, creationDate: Date, icon: String, color: String): Long
    suspend fun updateDeck(deck: Deck): Boolean
    suspend fun deleteDeckById(deckWithCards: DeckWithCards): Boolean
}