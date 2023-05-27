package nl.recall.domain.deck

import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory

@Factory
class ResetAlgorithm(private val cardRepository: CardRepository) {
    suspend operator fun invoke(deckId: Long): Boolean {
        return cardRepository.resetAlgorithm(deckId)
    }
}