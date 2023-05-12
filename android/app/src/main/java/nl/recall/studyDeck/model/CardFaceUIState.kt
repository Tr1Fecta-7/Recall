package nl.recall.studyDeck.model

enum class CardFaceUIState(val angle: Float) {
    Front(0f) {
        override val next: CardFaceUIState
            get() = Back
    },
    Back(180f) {
        override val next: CardFaceUIState
            get() = Front
    };

    abstract val next: CardFaceUIState
}