package nl.recall.data.communityDeck.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import nl.recall.data.communityDeck.mappers.CommunityCardMapper.toRequest
import nl.recall.data.communityDeck.network.request.CommunityDeckPostRequest
import nl.recall.data.communityDeck.network.request.CommunityDeckPutRequest
import nl.recall.data.communityDeck.network.response.CommunityDeckResponse
import nl.recall.data.network.HttpClientProvider
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.model.DeckWithCards
import org.koin.core.annotation.Factory

@Factory
class CommunityDeckService(private val httpClientProvider: HttpClientProvider) {
	suspend fun getCommunityDecks(): List<CommunityDeckResponse> {
		return httpClientProvider.client.get("/api/v1/deck").body()
	}

	suspend fun getCommunityDecks(title: String): List<CommunityDeckResponse> {
		return httpClientProvider.client.get("/api/v1/deck") {
			url {
				parameters.append("title", title)
			}
		}.body()
	}

	suspend fun publishDeck(deckWithCards: DeckWithCards) {
		httpClientProvider.client.post("/api/v1/deck") {
			contentType(ContentType.Application.Json)
			setBody(
				CommunityDeckPostRequest(
					title = deckWithCards.deck.title,
					icon = deckWithCards.deck.icon,
					color = deckWithCards.deck.color,
					cards = deckWithCards.cards.toRequest()
				)
			)
		}
	}

	suspend fun updateDeck(communityDeck: CommunityDeck) {
		httpClientProvider.client.put("/api/v1/deck/${communityDeck.id}") {
			contentType(ContentType.Application.Json)
			setBody(
				CommunityDeckPutRequest(
					id = communityDeck.id,
					title = communityDeck.title,
					icon = communityDeck.icon,
					color = communityDeck.color,
					downloads = communityDeck.downloads
				)
			)
		}
	}

	suspend fun getCommunityDeckById(id: Long): CommunityDeckResponse {
		return httpClientProvider.client.get("/api/v1/deck/$id").body()
	}
}