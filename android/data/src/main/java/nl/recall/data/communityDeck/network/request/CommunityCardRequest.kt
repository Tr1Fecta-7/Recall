package nl.recall.data.communityDeck.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCardRequest(
    val front: String,
    val back: String
)
