package nl.recall.domain.deck

import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class SaveDeckAndGetId(private val deckRepository: DeckRepository) {
	suspend operator fun invoke(
        title: String,
        creationDate: Date,
        icon: String,
        color: String,
    ): Long {
		return deckRepository.saveDeckAndGetId(
			title = title,
			creationDate = creationDate,
			icon = icon,
			color = color
		)
	}
}