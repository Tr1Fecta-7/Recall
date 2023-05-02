package nl.recall.data.deck.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import nl.recall.data.deck.models.CardEntity
import nl.recall.domain.deck.model.Card
import org.koin.core.annotation.Singleton

@Dao
interface CardDao {

    @Transaction
    @Query("SELECT * FROM card WHERE deck_id = :deckId AND (card.front LIKE :query or card.back LIKE :query)")
    suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<CardEntity>?

    @Insert
    suspend fun insert(cardEntity: CardEntity)


}