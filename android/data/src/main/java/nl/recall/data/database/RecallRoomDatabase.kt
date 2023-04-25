package nl.recall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.recall.data.models.Card
import nl.recall.data.models.Deck

@Database(entities = [Deck::class, Card::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecallRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao
    abstract fun cardDao(): CardDao

    companion object {
        private const val DATABASE_NAME = "RECALL_DATABASE"

        @Volatile
        private var INSTANCE: RecallRoomDatabase? = null

        fun getDatabase(context: Context): RecallRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RecallRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}