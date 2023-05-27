package nl.recall.presentation.studyDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDeckWithCardsWithCorrectDueDate
import nl.recall.domain.deck.ResetAlgorithm
import nl.recall.domain.deck.UpdateCard
import nl.recall.domain.deck.UpdateCardWithNewDate
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
    private val getDeckById: GetDeckWithCardsWithCorrectDueDate,
    private val updateCardWithNewDate: UpdateCardWithNewDate,
    private val updateCard: UpdateCard,
    private val resetAlgorithm: ResetAlgorithm,
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deckWithCards = MutableStateFlow<DeckWithCards?>(null)
    val deckWithCards: StateFlow<DeckWithCards?> by lazy {
        getDeckWithCards()
        _deckWithCards.asStateFlow()
    }

    private val _nextCardAvailability = MutableStateFlow(true)
    val nextCardAvailability: StateFlow<Boolean> = _nextCardAvailability.asStateFlow()

    private val _cards = MutableStateFlow<ArrayList<Card>>(arrayListOf())

    private val _progress = MutableStateFlow(0.0000f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _iterator = MutableStateFlow(0)
    val iterator: StateFlow<Int> = _iterator.asStateFlow()

    private val _deckSize = MutableStateFlow(0)
    val deckSize: StateFlow<Int> = _deckSize.asStateFlow()

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
                    deckWithCards.value?.let {
                        _cards.value = ArrayList(it.cards)
                        _currentCard.value = it.cards[iterator.value]
                        _deckSize.value = it.cards.size
                    }

                    _state.value = UIState.NORMAL
                }
            _deckWithCards.value?.let {
                if (iterator.value + 1 < it.cards.size) {
                    _nextCard.value = it.cards[iterator.value + 1]
                } else {
                    _nextCard.value = null
                }
            }
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }
    }

    fun onSwipeCard(direction: SwipeDirection, card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            _nextCardAvailability.value = false
            try {
                if (direction == SwipeDirection.LEFT) {
                    updateCardWithNewDate(
                        Card(
                            id = card.id,
                            front = card.front,
                            back = card.back,
                            dueDate = card.dueDate,
                            deckId = card.deckId,
                            successStreak = (card.successStreak + 1)
                        )
                    )
                } else {
                    val newCard =
                        Card(
                            id = card.id,
                            front = card.front,
                            back = card.back,
                            dueDate = Date(),
                            deckId = card.deckId,
                            successStreak = 0
                        )
                    _cards.value.add(newCard)
                    updateCard(newCard)
                    _deckSize.value = _cards.value.size
                }

            _iterator.value++
            _progress.value = (iterator.value.toFloat() / _cards.value.size.toFloat())

            if (iterator.value < _cards.value.size) {
                _currentCard.value = _cards.value[iterator.value]
                _nextCardAvailability.value = true
            }
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }


    }

    fun getNextCard() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cards.value.let {
                    if (iterator.value + 1 < it.size) {
                        _nextCard.value = it[iterator.value + 1]
                    } else {
                        _nextCard.value = null
                    }
                }
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }

        }
    }

    fun resetDeck() {
        _state.value = UIState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _deckWithCards.value?.let {
//                    val cards: List<Card> = it.cards.map { card ->
//                        return@map card.copy(dueDate = Date())
//                    }
                    println(resetAlgorithm(it.deck.id))
                }
                _state.value = UIState.NORMAL
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }
    }
}