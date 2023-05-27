package nl.recall.domain.communityDeck

import nl.recall.domain.communityDeck.models.CommunityDeck
import org.koin.core.annotation.Factory

@Factory
class UpdateCommunityDeck(private val communityDeckRepository: CommunityDeckRepository) {
	suspend operator fun invoke(communityDeck: CommunityDeck) {
		return communityDeckRepository.updateDeck(communityDeck)
	}
}
