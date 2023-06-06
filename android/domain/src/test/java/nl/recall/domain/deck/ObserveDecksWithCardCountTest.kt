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
internal class ObserveDecksWithCardCountTest{
    @RelaxedMockK
    private lateinit var deckRepository: DeckRepository

    @InjectMockKs
    private lateinit var observeDecksWithCardCount: ObserveDecksWithCardCount

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input, when observing decks with card count, then verify repo is called with correct args`() = runTest {
        // When
        observeDecksWithCardCount()
        // Then
         coVerify {
             deckRepository.observeDecksWithCardCount()
         }
    }
}