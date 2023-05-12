package nl.recall.studyDeck.model

import androidx.compose.ui.graphics.Color
import nl.recall.theme.AppTheme

enum class BackgroundColors(val color: Color) {
    Correct(AppTheme.primary300),
    Wrong(AppTheme.red300),
    Normal(AppTheme.neutral50)
}