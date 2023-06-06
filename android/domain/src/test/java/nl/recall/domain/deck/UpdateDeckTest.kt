package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.DeckRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class UpdateDeckTest{
    @RelaxedMockK
    private lateinit var deckRepository: DeckRepository

    @InjectMockKs
    private lateinit var updateDeck: UpdateDeck

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input, when updating the deck, then verify repo is called with correct args`() = runTest {
        // Given
        val date = Date()
        val deck = Deck(
            id = 1,
            title = "Hello",
            creationDate = date,
            icon = "\uD83D\uDD25",
            color = "#2596be"
        )
        // When
        updateDeck(deck)

        // Then
        coVerify {
            deckRepository.updateDeck(deck)
        }
    }
}