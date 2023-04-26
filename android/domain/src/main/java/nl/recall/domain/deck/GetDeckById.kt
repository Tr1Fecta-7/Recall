package nl.recall.domain.deck


import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class GetDeckById(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(id: Long): DeckWithCards {
        return deckRepository.getDeckById(id)
    }
}