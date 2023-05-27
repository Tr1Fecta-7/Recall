package nl.recall.domain.communityDeck

import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.DeckWithCards

interface CommunityDeckRepository {
    suspend fun getAllCommunityDecks(): List<CommunityDeck>

    suspend fun getAllCommunityDecks(title: String): List<CommunityDeck>

    suspend fun publishDeck(deckWithCards: DeckWithCards)

    suspend fun updateDeck(communityDeck: CommunityDeck)

    suspend fun getCommunityDeckById(id: Long): CommunityDeck
}