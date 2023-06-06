package nl.recall.domain.onboarding

import nl.recall.domain.communityDeck.CommunityDeckRepository
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.OnboardingRepository
import org.koin.core.annotation.Factory

@Factory
class SetOnboardingCompleted(private val onboardingRepository: OnboardingRepository) {
    suspend operator fun invoke(completed: Boolean) {
        return onboardingRepository.setOnboardingCompleted(completed)
    }
}
