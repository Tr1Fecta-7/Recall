package nl.recall.domain.deck

import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class DeleteDeckTest {
    @RelaxedMockK
    private lateinit var deckRepository: DeckRepository

    @InjectMockKs
    private lateinit var deleteDeck: DeleteDeck

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input when deleting the deck then verify repo is called with correct args`() =
        runTest {
            // Given
            val date: Date = Date()
            val deckWithCards = DeckWithCards(
                deck = Deck(
                    0, "titleDeck", date, icon = "\uD83D\uDD25",
                    color = "#2596be"
                ),
                cards = listOf()
            )

            // When
            deleteDeck(deckWithCards)

            // Then
            coVerify {
                deckRepository.deleteDeckById(deckWithCards)
            }
        }
}