package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class UpdateDateCardTest{
    @RelaxedMockK
    private lateinit var cardRepository: CardRepository

    @InjectMockKs
    private lateinit var updateCard: UpdateCard

    @Test
    fun `given input, when updating the card, then verify repo is called with correct args`() = runTest {
        // Given
        val date = Date()
        val card = Card(0, "front", "back", date, 0, 0 )

        // When
        updateCard(
            card
        )


        // Then
        coVerify {
            cardRepository.updateCard(
                card
            )
        }
    }
}