package nl.recall.data.communityDeck.mappers

import nl.recall.data.communityDeck.network.request.CommunityCardRequest
import nl.recall.data.communityDeck.network.response.CommunityCardResponse
import nl.recall.domain.communityDeck.models.CommunityCard
import nl.recall.domain.deck.model.Card

object CommunityCardMapper {
    internal fun List<CommunityCardResponse>.toDomain(): List<CommunityCard> {
        return map { it.toDomain() }
    }

    fun CommunityCardResponse.toDomain(): CommunityCard {
        return CommunityCard(
            back = back,
            front = front,
            id = id
        )
    }

    internal fun List<Card>.toRequest(): List<CommunityCardRequest> {
        return map { it.toRequest() }
    }

    fun Card.toRequest(): CommunityCardRequest {
        return CommunityCardRequest(
            back = back,
            front = front
        )
    }
}