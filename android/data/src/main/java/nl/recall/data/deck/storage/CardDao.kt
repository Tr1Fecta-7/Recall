package nl.recall.data.deck.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import nl.recall.data.deck.models.CardEntity
import nl.recall.domain.deck.model.Card
import org.koin.core.annotation.Singleton
import java.util.Date

@Dao
interface CardDao {

    @Transaction
    @Query("SELECT * FROM card WHERE deck_id = :deckId AND (card.front LIKE :query or card.back LIKE :query)")
    suspend fun getCardsBySearchQuery(deckId: Long, query: String): List<CardEntity>

    @Transaction
    @Query("SELECT * FROM card where deck_id = :deckId AND card.id = :cardId")
    suspend fun getCardById(deckId: Long, cardId: Long): CardEntity?

    @Insert
    suspend fun insert(cardEntity: CardEntity): Long

    @Update
    suspend fun updateCard(cardEntity: CardEntity): Int

    @Transaction
    @Query("UPDATE card SET due_date = :date WHERE id = :id")
    suspend fun updateDateCard(id: Long, date: Date): Int
}