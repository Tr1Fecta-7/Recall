package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockInjector
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class SaveCardTest {


    @RelaxedMockK
    private lateinit var cardRepository: CardRepository

    @InjectMockKs
    private lateinit var saveCard: SaveCard

    @Test
    fun `given input, when when saving the card, then verify repo is called with correct args`() = runTest {
        // Given
        val date = Date()

        // When
        saveCard(
            front = "Hello",
            back = "world",
            dueDate = date,
            deckId = 90,
            successStreak = 50,
        )

        // Then
        coVerify {
            cardRepository.saveCard(
                front = "Hello",
                back = "world",
                dueDate = date,
                deckId = 90,
                successStreak = 50
            )
        }
    }

}

