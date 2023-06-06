package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.recall.domain.repositories.DeckRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetDeckByIdTest{
    @RelaxedMockK
    private lateinit var deckRepository: DeckRepository

    @InjectMockKs
    private lateinit var getDeckById: GetDeckById

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input when getting the deck then verify repo is called with correct args`() = runTest{

        // Given
        val deckId = 90L

        // When
        getDeckById(deckId)

        // Then
        coVerify {
            deckRepository.getDeckById(deckId)
        }
    }
}