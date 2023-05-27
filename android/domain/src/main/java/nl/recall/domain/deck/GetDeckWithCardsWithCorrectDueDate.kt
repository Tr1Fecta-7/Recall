package nl.recall.domain.deck

import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.stream.Collectors


@Factory
class GetDeckWithCardsWithCorrectDueDate(private val deckRepository: DeckRepository) {
    suspend operator fun invoke(id: Long): DeckWithCards {

        val response = deckRepository.getDeckById(id)
        return DeckWithCards(response.deck, response.cards.stream().filter {

            val fmt = SimpleDateFormat("yyyyMMdd")
            return@filter fmt.format(Date()) >= fmt.format(it.dueDate)

        }.collect(Collectors.toList()))
    }
}