package nl.recall.domain.deck

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class UpdateDeck(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(deck: Deck): Boolean{
        return deckRepository.updateDeck(deck)
    }
}