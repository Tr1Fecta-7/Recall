package nl.recall.domain.deck


import kotlinx.coroutines.flow.Flow
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory

@Factory
class ObserveDeckById(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(id: Long): Flow<DeckWithCards?> {
        return deckRepository.observeDeckById(id)
    }
}