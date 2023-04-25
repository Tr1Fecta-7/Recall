package nl.recall.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.recall.data.models.Card

@Database(entities = [Card::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class CardRoomDatabase : RoomDatabase() {

    abstract fun deckDao(): DeckDao

    companion object {
        private const val DATABASE_NAME = "DECK_DATABASE"

        @Volatile
        private var INSTANCE: CardRoomDatabase? = null

        fun getDatabase(context: Context): CardRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(CardRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CardRoomDatabase::class.java, DATABASE_NAME
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