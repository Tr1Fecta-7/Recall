package nl.recall.data.deck

import android.content.res.Resources
import nl.recall.data.deck.mappers.CardMapper.toDomain
import nl.recall.data.deck.storage.CardDao
import nl.recall.data.deck.storage.DeckDao
import nl.recall.domain.deck.model.Card
import nl.recall.domain.repositories.CardRepository
import org.koin.core.annotation.Factory

@Factory
class RemoteCardRepository(private val cardDao: CardDao): CardRepository {
    override suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<Card> {
        val queryForDatabase = String.format("%%s%", query)
        println(queryForDatabase)

        return cardDao.getCardsBySearchQuery(deckId, queryForDatabase)?.toDomain() ?: throw Resources.NotFoundException()
    }
}