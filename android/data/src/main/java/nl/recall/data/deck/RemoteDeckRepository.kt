package nl.recall.data.deck


import android.content.res.Resources.NotFoundException
import nl.recall.data.deck.mappers.DeckWithCardCountMapper.toDomain
import nl.recall.data.deck.mappers.DeckWithCardsMapper.toDomain
import nl.recall.data.deck.models.DeckEntity
import nl.recall.data.deck.storage.DeckDao
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.domain.repositories.DeckRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class RemoteDeckRepository(private val deckDao: DeckDao) : DeckRepository {
    override suspend fun getDeckById(id: Long): DeckWithCards {
        return deckDao.getDeckById(id)?.toDomain() ?: throw NotFoundException()
    }

    override suspend fun getDeckWithCardCount(): Map<Deck, Int> {
        return deckDao.getDecksWithCardCount().toDomain()
    }

    override suspend fun searchDeckWithCardCount(title: String): Map<Deck, Int> {
        return deckDao.searchDecksWithCardCount("%$title%").toDomain()
    }

    override suspend fun saveDeck(
        title: String,
        creationDate: Date,
        icon: String,
        color: String
    ): Boolean {
        val deckEntityRow: Long =
            deckDao.insert(
                DeckEntity(
                    id = 0,
                    title = title,
                    creationDate = creationDate,
                    icon = icon,
                    color = color
                )
            )
        return deckEntityRow >= 0

    }

    override suspend fun updateDeck(deck: Deck): Boolean {
        return deckDao.updateDeck(
            DeckEntity(deck.id, deck.title, deck.creationDate, deck.icon, deck.color)
        ) >= 0
    }
}