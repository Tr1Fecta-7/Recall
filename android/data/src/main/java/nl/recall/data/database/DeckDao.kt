package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.StateFlow
import nl.recall.data.models.Deck
import nl.recall.data.models.DeckWithCards

@Dao
interface DeckDao {
    @Transaction
    @Query("SELECT * from deck")
    fun getDecks(): List<DeckWithCards>

    @Insert
    suspend fun insert(deck: Deck)

    @Insert
    suspend fun insert(deck: List<Deck>)

    @Delete
    suspend fun delete(deck: Deck)

    @Query("DELETE from deck")
    suspend fun deleteAll()
}