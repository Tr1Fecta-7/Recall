package nl.recall.domain.deck

import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory
import java.util.Calendar
import java.util.Date
import kotlin.math.ceil
import kotlin.math.roundToInt

@Factory
class UpdateCardWithNewDate(private val cardRepository: CardRepository) {
    val STRENGTH_ALGORITHM = 0.2

//    fun test(): String {
//        return cardRepository.test("hello")
//    }

    suspend operator fun invoke(card: Card): Boolean {
        return cardRepository.updateCard(
            Card(
                id = card.id,
                front = card.front,
                back = card.back,
                dueDate = calculateNewDueDate(card.successStreak),
                deckId = card.id,
                successStreak = card.successStreak
            )
        )
    }


    private fun calculateNewDueDate(successStreak: Long): Date {
        val days = ceil(STRENGTH_ALGORITHM*(successStreak*successStreak)).toInt()

        var newDueDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = newDueDate
        calendar.add(Calendar.DATE, (days))
        newDueDate = calendar.time

        return newDueDate
    }
}