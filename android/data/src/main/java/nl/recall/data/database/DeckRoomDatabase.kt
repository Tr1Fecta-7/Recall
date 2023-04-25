package nl.recall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.recall.data.models.Deck

@Database(entities = [Deck::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DeckRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao

    companion object {
        private const val DATABASE_NAME = "DECK_DATABASE"

        @Volatile
        private var INSTANCE: DeckRoomDatabase? = null

        fun getDatabase(context: Context): DeckRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(DeckRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DeckRoomDatabase::class.java, DATABASE_NAME
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