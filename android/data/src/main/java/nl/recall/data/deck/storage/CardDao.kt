package nl.recall.data.deck.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.recall.data.deck.models.CardEntity

@Dao
interface CardDao {

    @Insert
    suspend fun insert(cardEntity: CardEntity)

    @Insert
    suspend fun insert(cardEntity: List<CardEntity>)

    @Delete
    suspend fun delete(cardEntity: CardEntity)

    @Query("DELETE from card")
    suspend fun deleteAll()

}