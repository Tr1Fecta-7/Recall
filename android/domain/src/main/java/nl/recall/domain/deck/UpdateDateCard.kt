package nl.recall.domain.deck

import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class UpdateDateCard(private val cardRepository: CardRepository) {
    suspend operator fun invoke(id: Long, date: Date): Boolean{
        return cardRepository.updateDateCard(id, date)
    }
}