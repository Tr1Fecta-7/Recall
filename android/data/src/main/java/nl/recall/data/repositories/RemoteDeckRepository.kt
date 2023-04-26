package nl.recall.data.repositories


import android.content.Context
import nl.recall.data.database.DeckDao
import nl.recall.data.database.RecallRoomDatabase
import nl.recall.data.mappers.DeckWithCardCountMapper.toDomain
import nl.recall.data.mappers.DeckWithCardsMapper.toDomain
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.repositories.DeckRepository
import nl.recall.domain.deck.model.DeckWithCards
import org.koin.core.annotation.Factory

@Factory
class RemoteDeckRepository(private val deckDao: DeckDao) : DeckRepository {
    override suspend fun getDeckById(id: Long): DeckWithCards? {
        return deckDao.getDeckById(id)?.toDomain()
    }

    override suspend fun getDeckWithCardCount(): Map<Deck, Int> {
        return deckDao.getDecksWithCardCount().toDomain()
    }
}