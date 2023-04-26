package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Transaction
import nl.recall.data.models.DeckEntity
import nl.recall.data.models.DeckWithCardsEntity

@Dao
interface DeckDao {
    @Transaction
    @Query("SELECT * from deck where id = :id")
    suspend fun getDeckById(id: Long): DeckWithCardsEntity?

    @Transaction
    @MapInfo(valueColumn = "count")
    @Query("SELECT deck.*, count(card.id) AS count FROM deck LEFT JOIN card ON deck.id = card.deck_id GROUP BY deck.id")
    suspend fun getDecksWithCardCount(): Map<DeckEntity, Int>

    @Insert
    suspend fun insert(deckEntity: DeckEntity)

    @Insert
    suspend fun insert(deckEntity: List<DeckEntity>)

    @Delete
    suspend fun delete(deckEntity: DeckEntity)

    @Query("DELETE from deck")
    suspend fun deleteAll()
}