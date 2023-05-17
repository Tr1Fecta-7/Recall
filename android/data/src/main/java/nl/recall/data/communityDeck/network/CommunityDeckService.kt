package nl.recall.data.communityDeck.network

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.recall.data.communityDeck.mappers.CommunityCardMapper.toRequest
import nl.recall.data.communityDeck.network.request.CommunityDeckRequest
import nl.recall.data.network.HttpClientProvider
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.DeckWithCards
import org.koin.core.annotation.Factory

@Factory
class CommunityDeckService(private val httpClientProvider: HttpClientProvider) {
    suspend fun getCommunityDecks(): List<CommunityDeck> {
        return httpClientProvider.client.get("/api/v1/deck")
    }

    suspend fun publishDeck(deckWithCards: DeckWithCards) {
        return httpClientProvider.client.post("api/v1/deck") {
            contentType(ContentType.Application.Json)
            body = CommunityDeckRequest(
                title = deckWithCards.deck.title,
                icon = deckWithCards.deck.icon,
                color = deckWithCards.deck.color,
                cards = deckWithCards.cards.toRequest()
            )
        }
    }
}