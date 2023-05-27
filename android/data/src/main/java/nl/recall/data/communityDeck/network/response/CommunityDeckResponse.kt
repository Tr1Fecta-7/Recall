package nl.recall.data.communityDeck.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityDeckResponse(
	val cards: List<CommunityCardResponse>,
	val color: String,
	val creation: String,
	val downloads: Long,
	val icon: String,
	val id: Long,
	val title: String,
)