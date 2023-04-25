package nl.recall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.recall.data.models.CardResult

@Dao
interface CardDao {

    @Insert
    suspend fun insert(cardResult: CardResult)

    @Insert
    suspend fun insert(cardResult: List<CardResult>)

    @Delete
    suspend fun delete(cardResult: CardResult)

    @Query("DELETE from card")
    suspend fun deleteAll()

}