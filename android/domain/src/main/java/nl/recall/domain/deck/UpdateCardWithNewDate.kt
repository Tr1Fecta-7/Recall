package nl.recall.domain.deck

import nl.recall.domain.deck.model.AlgorithmStrength
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory
import java.util.Calendar
import java.util.Date
import kotlin.math.ceil

@Factory
class UpdateCardWithNewDate(private val cardRepository: CardRepository) {

    suspend operator fun invoke(card: Card, algorithmStrength: AlgorithmStrength): Boolean {
        return cardRepository.updateCard(
            Card(
                id = card.id,
                front = card.front,
                back = card.back,
                dueDate = calculateNewDueDate(card.successStreak, algorithmStrength),
                deckId = card.deckId,
                successStreak = card.successStreak
            )
        )
    }


    private fun calculateNewDueDate(successStreak: Long, algorithmStrength: AlgorithmStrength): Date {
        val days = ceil(algorithmStrength.strength*(successStreak*successStreak)).toInt()

        var newDueDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = newDueDate
        calendar.add(Calendar.DATE, (days))
        newDueDate = calendar.time

        return newDueDate
    }
}