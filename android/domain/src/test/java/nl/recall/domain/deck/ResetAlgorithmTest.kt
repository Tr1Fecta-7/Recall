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
internal class ResetAlgorithmTest{
    @RelaxedMockK
    private lateinit var cardRepository: CardRepository

    @InjectMockKs
    private lateinit var resetAlgorithm: ResetAlgorithm

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input, when resetting algorithm, then verify repo is called with correct args`() = runTest {
        //given
        val deckId = 90L
        // When
        resetAlgorithm(deckId)
        // Then
        coVerify {
            cardRepository.resetAlgorithm(deckId)
        }
    }
}
