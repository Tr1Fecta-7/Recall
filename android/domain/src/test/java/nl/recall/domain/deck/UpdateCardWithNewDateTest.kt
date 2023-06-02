package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Calendar
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class UpdateCardWithNewDateTest {

    @RelaxedMockK
    private lateinit var cardRepository:CardRepository

    @InjectMockKs
    private lateinit var updateCardWithNewDate: UpdateCardWithNewDate


    @Test
    fun `given input, when updating the card, then verify repo is called with correct args and the due date is set correctly`() = runTest{
        val correctCombinations = HashMap<Long, Long>()
        correctCombinations[10] = 20
        correctCombinations[1] = 1
        correctCombinations[2] = 1
        correctCombinations[3] = 2
        correctCombinations[4] = 4
        correctCombinations[20] = 80

        for (map in correctCombinations) {
            // Given
            var date = Date()

            // When
            updateCardWithNewDate(Card(id=90, front = "Hello",
                back = "world",
                dueDate = date,
                deckId = 90,
                successStreak = map.key,))

            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, (map.value.toInt()))
            date = calendar.time

            // Then
            coVerify {
                cardRepository.updateCard(Card(
                    id = 90,
                    front = "Hello",
                    back = "world",
                    dueDate = date,
                    deckId = 90,
                    successStreak = map.key
                )
                )
            }
        }

    }
}