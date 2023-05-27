package nl.recall.domain.communityDeck

import nl.recall.domain.communityDeck.models.CommunityDeck
import org.koin.core.annotation.Factory

@Factory
class GetCommunityDeckById(private val communityDeckRepository: CommunityDeckRepository) {
	suspend operator fun invoke(id: Long): CommunityDeck {
		return communityDeckRepository.getCommunityDeckById(id)
	}
}
