package nl.recall.domain.deck

import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class DeleteDeck(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(deckWithCards: DeckWithCards): Boolean{
        return deckRepository.deleteDeckById(deckWithCards)
    }
}