package nl.recall.data.communityDeck.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CommunityDeckRequest(
    val title: String,
    val icon: String,
    val color: String,
    val cards: List<CommunityCardRequest>
)
