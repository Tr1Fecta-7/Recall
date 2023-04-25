package nl.recall.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("card")
data class CardResult(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "front")
    var front: String,

    @ColumnInfo(name = "back")
    var back: String,

    @ColumnInfo(name = "due_date")
    var dueDate: Date,

    @ColumnInfo(name = "deck_id")
    var deckId: Long
)
