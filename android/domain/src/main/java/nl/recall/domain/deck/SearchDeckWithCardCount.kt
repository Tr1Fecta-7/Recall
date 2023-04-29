package nl.recall.domain.deck

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class SearchDeckWithCardCount(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(title: String): Map<Deck, Int> {
        return deckRepository.searchDeckWithCardCount(title)
    }
}