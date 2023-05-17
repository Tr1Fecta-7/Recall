package nl.recall.data.communityDeck.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityDeckResponse(
    val cards: List<CardResponse>,
    val color: String,
    val creation: String,
    val downloads: Int,
    val icon: String,
    val id: Long,
    val title: String
)