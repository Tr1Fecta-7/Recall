package nl.recall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.recall.data.models.CardResult
import nl.recall.data.models.DeckResult

@Database(entities = [DeckResult::class, CardResult::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecallRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao

    companion object {
        private const val DATABASE_NAME = "RECALL_DATABASE"

        @Volatile
        private var INSTANCE: RecallRoomDatabase? = null

        fun getDatabase(context: Context): RecallRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecallRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}