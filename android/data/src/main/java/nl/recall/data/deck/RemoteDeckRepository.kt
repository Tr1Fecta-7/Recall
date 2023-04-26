package nl.recall.data.deck


import nl.recall.data.deck.storage.DeckDao
import nl.recall.data.deck.mappers.DeckWithCardCountMapper.toDomain
import nl.recall.data.deck.mappers.DeckWithCardsMapper.toDomain
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