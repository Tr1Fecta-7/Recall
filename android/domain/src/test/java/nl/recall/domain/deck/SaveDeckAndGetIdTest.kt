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
import java.util.Date

@ExtendWith(MockKExtension::class)
internal class SaveDeckAndGetIdTest{
    @RelaxedMockK
    private lateinit var deckRepository: DeckRepository

    @InjectMockKs
    private lateinit var saveDeckAndGetId: SaveDeckAndGetId

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given input, when saving the deck, then verify repo is called with correct args`() = runTest {
        // Given
        val date = Date()

        // When
        saveDeckAndGetId(
            title = "Hello",
            creationDate = date,
            icon = "world",
            color = "red",
        )

        // Then
        coVerify {
            deckRepository.saveDeckAndGetId(
                title = "Hello",
                creationDate = date,
                icon = "world",
                color = "red"
            )
        }
    }
}