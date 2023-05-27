package nl.recall.data.communityDeck.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCardResponse(
    val back: String,
    val front: String,
    val id: Long
)