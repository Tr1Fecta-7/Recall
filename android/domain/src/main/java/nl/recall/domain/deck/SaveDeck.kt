package nl.recall.domain.deck

import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class SaveDeck(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(
        title: String,
        creationDate: Date,
        icon: String,
        color: String
    ): Boolean {
        return deckRepository.saveDeck(
            title = title,
            creationDate = creationDate,
            icon = icon,
            color = color
        ) >= 0
    }
}