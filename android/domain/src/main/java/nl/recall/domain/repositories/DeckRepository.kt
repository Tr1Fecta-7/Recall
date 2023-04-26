package nl.recall.domain.repositories

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards

interface DeckRepository {
    suspend fun getDeckById(id: Long): DeckWithCards
    suspend fun getDeckWithCardCount(): Map<Deck, Int>
}