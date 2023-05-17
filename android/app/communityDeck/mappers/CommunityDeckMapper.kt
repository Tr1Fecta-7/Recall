package nl.recall.data.communityDeck.mappers

import nl.recall.data.communityDeck.mappers.CommunityCardMapper.toDomain
import nl.recall.data.communityDeck.network.response.CommunityDeckResponse
import nl.recall.domain.communityDeck.models.CommunityDeck

object CommunityDeckMapper {
    internal fun List<CommunityDeckResponse>.toDomain(): List<CommunityDeck> {
        return map { it.toDomain() }
    }

    fun CommunityDeckResponse.toDomain(): CommunityDeck {
        return CommunityDeck(
            cards = cards.toDomain(),
            color = color,
            creation = creation,
            downloads = downloads,
            icon = icon,
            id = id,
            title = title
        )
    }
}