package nl.recall.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "deck")
data class Deck (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "creation_date")
    var creationDate : Date,

    @ColumnInfo(name = "icon")
    var icon : String,

    @ColumnInfo(name = "background_color")
    var backgroundColor: String,
)
