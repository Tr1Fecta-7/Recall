package nl.recall.domain.communityDeck.models

data class CommunityDeck(
    val cards: List<CommunityCard>,
    val color: String,
    val creation: String,
    val downloads: Int,
    val icon: String,
    val id: Long,
    val title: String
)
