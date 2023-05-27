package nl.recall.data.communityDeck.network.request

import kotlinx.serialization.Serializable

@Serializable
data class CommunityDeckPutRequest(
	val id: Long,
	val title: String,
	val icon: String,
	val color: String,
	val downloads: Long,
)
