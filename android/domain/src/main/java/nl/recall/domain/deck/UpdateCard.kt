package nl.recall.domain.deck

import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateCard(private val cardRepository: CardRepository) {
    suspend operator fun invoke(card: Card): Boolean {
        return cardRepository.updateCard(card)
    }
}