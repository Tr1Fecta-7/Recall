package nl.recall.presentation.studyDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDeckById
import nl.recall.domain.deck.UpdateCard
import nl.recall.domain.deck.UpdateDateCard
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
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

    private val _state = MutableStateFlow(UIState.EMPTY)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deckWithCards = MutableStateFlow<DeckWithCards?>(null)
    val deckWithCards: StateFlow<DeckWithCards?> by lazy {
        fetchDeckWithCards()
        _deckWithCards.asStateFlow()
    }

    private val _progress = MutableStateFlow(0.0000f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _iterator = MutableStateFlow(0)
    val iterator: StateFlow<Int> = _iterator.asStateFlow()

    private val _progressStep = MutableStateFlow(0.0000f)

    private fun fetchDeckWithCards() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = UIState.LOADING
                _deckWithCards.value = getDeckById(args.deckId)
                if (_deckWithCards.value?.cards.isNullOrEmpty()) {
                    _progressStep.value = (0.000f)
                    _state.value = UIState.EMPTY
                } else {
                    _state.value = UIState.NORMAL
                    //Ask if this can be done better
                    _progressStep.value = (1.000f / _deckWithCards.value?.cards?.size!!)
                }

            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }
    }

    fun onSwipeCard(direction: SwipeDirection, card: Card) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (direction == SwipeDirection.LEFT) {
                    updateDateCard(card.id, Date())
                } else {
                    updateDateCard(card.id, Date())
                }
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }

            _progress.value += _progressStep.value
            _iterator.value++
        }


    }

}