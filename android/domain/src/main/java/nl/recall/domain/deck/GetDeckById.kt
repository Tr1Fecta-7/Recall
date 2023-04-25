package nl.recall.domain.deck

import nl.recall.domain.deck.data.DeckRepository
import nl.recall.domain.deck.model.Deck
import org.koin.core.annotation.Factory

@Factory
class GetDeckById(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(id: Long): Deck {
        return deckRepository.getDeckById(id)
    }
}