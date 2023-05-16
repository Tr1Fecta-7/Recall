package nl.recall.domain.communityDeck

import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.Deck

interface CommunityDeckRepository {
    suspend fun getAllCommunityDecks(): List<CommunityDeck>

    suspend fun publishDeck(deck: Deck)
}