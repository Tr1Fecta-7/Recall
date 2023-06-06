package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import nl.recall.domain.repositories.CardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetCardsBySearchQueryTest{
    @RelaxedMockK
    private lateinit var cardRepository: CardRepository

    @InjectMockKs
    private lateinit var getCardsBySearchQuery: GetCardsBySearchQuery

    @Test
    fun `given input when getting the cards then verify repo is called with correct args`() = runTest {
        // Given
        val deckId = 90L
        val query = "Hello"

        // When
        getCardsBySearchQuery(deckId, query)

        // Then
        coVerify {
            cardRepository.getCardsBySearchQuery(deckId, query)
        }
    }
}