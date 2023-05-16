package nl.recall.data.communityDeck.mappers

import nl.recall.data.communityDeck.network.response.CardResponse
import nl.recall.domain.communityDeck.models.CommunityCard

object CommunityCardMapper {
    internal fun List<CardResponse>.toDomain(): List<CommunityCard> {
        return map { it.toDomain() }
    }

    fun CardResponse.toDomain(): CommunityCard {
        return CommunityCard(
            back = back,
            front = front,
            id = id
        )
    }
}