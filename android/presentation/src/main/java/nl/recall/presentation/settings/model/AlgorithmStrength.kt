package nl.recall.presentation.settings.model

enum class AlgorithmStrength(val strengthName: String, val strength: Double) {
    EASY("Easy", 0.05),
    NORMAL("Normal", 0.2),
    HARD("Hard", 0.5),
}