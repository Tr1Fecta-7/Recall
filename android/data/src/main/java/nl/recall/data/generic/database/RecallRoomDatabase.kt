package nl.recall.data.generic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.recall.data.deck.models.CardEntity
import nl.recall.data.deck.models.DeckEntity
import nl.recall.data.deck.storage.CardDao
import nl.recall.data.deck.storage.DeckDao

@Database(entities = [DeckEntity::class, CardEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecallRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao

    companion object {
        private const val DATABASE_NAME = "RECALL_DATABASE"
        fun getDatabase(context: Context): RecallRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext, RecallRoomDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}