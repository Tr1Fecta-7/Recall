package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Transaction
import nl.recall.data.models.DeckResult
import nl.recall.data.models.DeckWithCardsResult

@Dao
interface DeckDao {
    @Transaction
    @Query("SELECT * from deck where id = :id")
    fun getDeckById(id: Long): DeckWithCardsResult

    @Transaction
    @MapInfo(valueColumn = "count")
    @Query("SELECT deck.*, count(*) AS count FROM deck LEFT JOIN card ON deck.id = card.deck_id GROUP BY deck.id")
    fun getDecksWithCardCount(): Map<Deck, Int>

    @Insert
    suspend fun insert(deckResult: DeckResult)

    @Insert
    suspend fun insert(deckResult: List<DeckResult>)

    @Delete
    suspend fun delete(deckResult: DeckResult)

    @Query("DELETE from deck")
    suspend fun deleteAll()
}