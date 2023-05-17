package nl.recall.data.communityDeck

import nl.recall.data.communityDeck.network.CommunityDeckService
import nl.recall.domain.communityDeck.CommunityDeckRepository
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.DeckWithCards
import org.koin.core.annotation.Factory

@Factory
class RemoteCommunityDeckRepository(
    private val communityDeckService: CommunityDeckService
) : CommunityDeckRepository {

    override suspend fun getAllCommunityDecks(): List<CommunityDeck> {
        return communityDeckService.getCommunityDecks()
    }

    override suspend fun publishDeck(deckWithCards: DeckWithCards) {
        communityDeckService.publishDeck(deckWithCards)
    }
}