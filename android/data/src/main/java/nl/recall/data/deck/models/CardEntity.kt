package nl.recall.data.deck.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("card")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "front")
    val front: String = "",

    @ColumnInfo(name = "back")
    val back: String = "",

    @ColumnInfo(name = "due_date")
    val dueDate: Date = Date(),

    @ColumnInfo(name = "deck_id")
    val deckId: Long = 0,

    @ColumnInfo(name = "success_streak")
    val successStreak: Long = 0
)
