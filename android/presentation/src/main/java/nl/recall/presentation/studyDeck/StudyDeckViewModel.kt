package nl.recall.presentation.studyDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDeckById
import nl.recall.domain.deck.UpdateDateCard
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.studyDeck.model.SwipeDirection
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import java.util.Date

@KoinViewModel
class StudyDeckViewModel(
    @InjectedParam private val args: StudyDeckViewModelArgs,
    private val getDeckById: GetDeckById,
    private val updateDateCard: UpdateDateCard
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deckWithCards = MutableStateFlow<DeckWithCards?>(null)
    val deckWithCards: StateFlow<DeckWithCards?> by lazy {
        getDeckWithCards()
        _deckWithCards.asStateFlow()
    }

    private val _wrongCards = MutableStateFlow<ArrayList<Card>>(arrayListOf())
    val wrongCards: StateFlow<ArrayList<Card>> = _wrongCards.asStateFlow()


    private val _progress = MutableStateFlow(0.0000f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _iterator = MutableStateFlow(0)
    val iterator: StateFlow<Int> = _iterator.asStateFlow()

    private val _currentCard = MutableStateFlow<Card?>(null)
    val currentCard: StateFlow<Card?> = _currentCard.asStateFlow()

    private val _nextCard = MutableStateFlow<Card?>(null)
    val nextCard: StateFlow<Card?> = _nextCard.asStateFlow()


    private fun getDeckWithCards() {
        _state.value = UIState.LOADING

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _deckWithCards.value = getDeckById(args.deckId)
                if (_deckWithCards.value?.cards.isNullOrEmpty()) {
                    _state.value = UIState.EMPTY
                } else {
                    deckWithCards?.value?.let {
                        _currentCard.value = it.cards[iterator.value]
                    }

                    _state.value = UIState.NORMAL
                }
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
            _deckWithCards.value?.let {
                if (iterator.value + 1 < it.cards.size) {
                    _nextCard.value = it.cards[iterator.value + 1]
                } else {
                    _nextCard.value = null
                }
            }
        }
    }

    fun onSwipeCard(direction: SwipeDirection, card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (direction == SwipeDirection.LEFT) {

                    updateDateCard(card.id, Date())
                } else {
                    wrongCards.value.add(card)
                    updateDateCard(card.id, Date())
                }


            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }
        _iterator.value++
        _deckWithCards.value?.let {
            _progress.value =
                (iterator.value.toFloat() / it.cards.size.toFloat())
            if (iterator.value < it.cards.size) {
                _currentCard.value = it.cards[iterator.value]
            }
        }
    }

    fun getNextCard() {
        viewModelScope.launch(Dispatchers.IO) {
            _deckWithCards.value?.let {
                if (iterator.value + 1 < it.cards.size) {
                    _nextCard.value = it.cards[iterator.value + 1]
                } else {
                    _nextCard.value = null
                }
            }
        }
    }
}