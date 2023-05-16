package nl.recall.data.deck.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import nl.recall.data.deck.models.DeckEntity
import nl.recall.data.deck.models.DeckWithCardsEntity

@Dao
interface DeckDao {
    @Transaction
    @Query("SELECT * from deck where id = :id")
    suspend fun getDeckById(id: Long): DeckWithCardsEntity?

    @Transaction
    @MapInfo(valueColumn = "count")
    @Query("SELECT deck.*, count(card.id) AS count FROM deck LEFT JOIN card ON deck.id = card.deck_id GROUP BY deck.id")
    suspend fun getDecksWithCardCount(): Map<DeckEntity, Int>

    @Transaction
    @MapInfo(valueColumn = "count")
    @Query("SELECT deck.*, count(card.id) AS count FROM deck LEFT JOIN card ON deck.id = card.deck_id WHERE deck.title LIKE :title GROUP BY deck.id")
    suspend fun searchDecksWithCardCount(title: String): Map<DeckEntity, Int>

    @Insert
    suspend fun insert(deckEntity: DeckEntity): Long

    @Insert
    suspend fun insert(deckEntity: List<DeckEntity>)

    @Delete
    suspend fun delete(deckEntity: DeckEntity): Int

    @Query("DELETE from deck")
    suspend fun deleteAll()

    @Update
    suspend fun updateDeck(deck: DeckEntity): Int
}