package nl.recall.domain.deck

import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory

@Factory
class GetCardById(private val cardRepository: CardRepository) {
    suspend operator fun invoke(deckId: Long, cardId: Long): Card {
        return cardRepository.getCardById(deckId, cardId)
    }
}