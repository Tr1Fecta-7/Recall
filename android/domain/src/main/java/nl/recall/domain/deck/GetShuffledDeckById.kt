package nl.recall.domain.deck

import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class GetShuffledDeckById(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(id: Long): DeckWithCards {
        val response = deckRepository.getDeckById(id)
        return DeckWithCards(response.deck, response.cards.shuffled())
    }
}