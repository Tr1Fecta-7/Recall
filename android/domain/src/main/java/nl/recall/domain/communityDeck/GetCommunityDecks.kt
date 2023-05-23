package nl.recall.domain.communityDeck

import nl.recall.domain.communityDeck.models.CommunityDeck
import org.koin.core.annotation.Factory

@Factory
class GetCommunityDecks(private val communityDeckRepository: CommunityDeckRepository) {
	suspend operator fun invoke(): List<CommunityDeck> {
		return communityDeckRepository.getAllCommunityDecks()
	}

	suspend operator fun invoke(title: String): List<CommunityDeck> {
		return communityDeckRepository.getAllCommunityDecks(title)
	}
}