package nl.recall.domain.onboarding

import nl.recall.domain.communityDeck.CommunityDeckRepository
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.OnboardingRepository
import org.koin.core.annotation.Factory

@Factory
class GetOnboardingCompleted(private val onboardingRepository: OnboardingRepository) {
    operator fun invoke(): Boolean {
        return onboardingRepository.getOnboardingCompleted()
    }
}
