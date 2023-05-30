package nl.recall.domain.deck


import kotlinx.coroutines.flow.Flow
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class ObserveDecksWithCardCount(private val deckRepository: DeckRepository) {

    suspend operator fun invoke(): Flow<Map<Deck, Int>> {
        return deckRepository.observeDecksWithCardCount()
    }
}