package nl.recall.deck.study.model

import androidx.compose.ui.graphics.Color
import nl.recall.theme.AppTheme

enum class BackgroundColors(val color: Color) {
    CORRECT(AppTheme.primary300),
    WRONG(AppTheme.red300),
    NORMAL(AppTheme.neutral50)
}