package nl.recall.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.StateFlow
import nl.recall.data.models.Deck

@Dao
interface DeckDao {

    @Query("SELECT * from deck")
    fun getDecks(): StateFlow<List<Deck>>

    @Insert
    suspend fun insert(game: Deck)

    @Insert
    suspend fun insert(game: List<Deck>)

    @Delete
    suspend fun delete(game: Deck)

    @Query("DELETE from deck")
    suspend fun deleteAll()

}