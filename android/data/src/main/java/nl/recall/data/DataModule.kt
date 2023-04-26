package nl.recall.data

import android.content.Context
import nl.recall.data.database.DeckDao
import nl.recall.data.database.RecallRoomDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class DataModule {
    @Single
    fun provideDatabase(context: Context): RecallRoomDatabase {
        return RecallRoomDatabase.getDatabase(context)
    }

    @Single
    fun getDeckDao(database: RecallRoomDatabase): DeckDao {
        return database.deckDao()
    }
}