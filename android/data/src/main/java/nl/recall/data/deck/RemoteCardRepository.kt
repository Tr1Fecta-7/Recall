package nl.recall.data.deck

import android.content.res.Resources
import nl.recall.data.deck.mappers.CardMapper.toDomain
import nl.recall.data.deck.models.CardEntity
import nl.recall.data.deck.storage.CardDao
import nl.recall.data.deck.storage.DeckDao
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class RemoteCardRepository(private val cardDao: CardDao): CardRepository {
    override suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<Card> {
        val queryForDatabase = "%$query%"

        return cardDao.getCardsBySearchQuery(deckId, queryForDatabase)?.toDomain() ?: throw Resources.NotFoundException()
    }

    override suspend fun saveCard(
        front: String,
        back: String,
        dueDate: Date,
        deckId: Long
    ): Boolean {
        val cardEntityRow: Long = cardDao.insert(CardEntity(
            id = 0,
            front = front,
            back = back,
            dueDate = dueDate,
            deckId = deckId,
        ))

        return cardEntityRow >= 0
    }
}