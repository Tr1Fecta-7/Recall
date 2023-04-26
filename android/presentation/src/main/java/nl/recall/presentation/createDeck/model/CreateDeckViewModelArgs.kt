package nl.recall.presentation.createDeck.model

import android.graphics.drawable.Icon
import java.util.Date

class CreateDeckViewModelArgs(
    val id: Long,
    val title: String,
    val color: String,
    val creationDate: Date,
    val icon: String
    )
