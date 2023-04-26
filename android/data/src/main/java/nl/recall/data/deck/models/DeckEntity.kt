package nl.recall.data.deck.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "deck")
data class DeckEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "creation_date")
    val creationDate : Date = Date(),

    @ColumnInfo(name = "icon")
    val icon : String = "",

    @ColumnInfo(name = "color")
    val color: String = "",
)
