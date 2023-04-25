package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.StateFlow
import nl.recall.data.models.Deck
import nl.recall.data.models.DeckWithCards

@Dao
interface DeckDao {
    @Transaction
    @Query("SELECT * from deck")
    fun getDecksWithCards(): List<DeckWithCards>

    @Transaction
    @MapInfo(valueColumn = "count")
    @Query("SELECT deck.*, count(*) AS count FROM deck LEFT JOIN card ON deck.id = card.deck_id GROUP BY deck.id")
    fun getDecksWithCardCount(): Map<Deck, Int>

    @Insert
    suspend fun insert(deck: Deck)

    @Insert
    suspend fun insert(deck: List<Deck>)

    @Delete
    suspend fun delete(deck: Deck)

    @Query("DELETE from deck")
    suspend fun deleteAll()
}