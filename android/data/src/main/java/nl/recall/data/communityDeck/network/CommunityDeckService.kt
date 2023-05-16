package nl.recall.data.communityDeck.network

import io.ktor.client.request.get
import nl.recall.data.network.HttpClientProvider
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.Deck
import org.koin.core.annotation.Factory

@Factory
class CommunityDeckService(private val httpClientProvider: HttpClientProvider) {
    suspend fun getCommunityDecks(): List<CommunityDeck> {
        return httpClientProvider.client.get("/api/v1/deck")
    }

    suspend fun publishDeck(deck: Deck) {
        // TODO: Write post
    }
}