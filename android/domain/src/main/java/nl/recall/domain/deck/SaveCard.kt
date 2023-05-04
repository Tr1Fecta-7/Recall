package nl.recall.domain.deck

import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class SaveCard(private val cardRepository: CardRepository) {
    suspend operator fun invoke(front: String, back: String, dueDate: Date, deckId: Long): Boolean {
        return cardRepository.saveCard(front = front, back = back, dueDate = dueDate, deckId = deckId)
    }
}