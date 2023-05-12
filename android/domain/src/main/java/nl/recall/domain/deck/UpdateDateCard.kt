package nl.recall.domain.deck

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.CardRepository
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class UpdateDateCard(private val cardRepository: CardRepository) {
    suspend operator fun invoke(id: Long, date: Date): Boolean{
        return cardRepository.updateDateCard(id, date)
    }
}