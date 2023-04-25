package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import nl.recall.data.models.Card
import nl.recall.data.models.Deck
import nl.recall.data.models.DeckWithCards

@Dao
interface CardDao {

    @Insert
    suspend fun insert(card: Card)

    @Insert
    suspend fun insert(card: List<Card>)

    @Delete
    suspend fun delete(card: Card)

    @Query("DELETE from card")
    suspend fun deleteAll()

}