package nl.recall.domain.deck


import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class GetDecksWithCardCount(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(): Map<Deck, Int> {
        return deckRepository.getDeckWithCardCount()
    }
}