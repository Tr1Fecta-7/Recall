package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.recall.domain.repositories.CardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetCardByIdTest{
    @RelaxedMockK
    private lateinit var cardRepository: CardRepository

    @InjectMockKs
    private lateinit var getCardById: GetCardById

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input when getting the card then verify repo is called with correct args`() = runTest {
        // Given
        val deckId = 90L
        val cardId = 50L

        // When
        getCardById(deckId, cardId)

        // Then
        coVerify {
            cardRepository.getCardById(deckId, cardId)
        }
    }

}