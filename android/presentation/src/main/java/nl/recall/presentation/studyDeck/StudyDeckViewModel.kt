package nl.recall.presentation.studyDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDeckById
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class StudyDeckViewModel(
    @InjectedParam private val args: StudyDeckViewModelArgs, private val getDeckById: GetDeckById
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.EMPTY)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deckWithCards = MutableStateFlow<DeckWithCards?>(null)
    val deckWithCards : StateFlow<DeckWithCards?> by lazy {
        fetchDeckWithCards()
        _deckWithCards.asStateFlow()
    }

    private fun fetchDeckWithCards() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = UIState.LOADING
                _deckWithCards.value = getDeckById(args.deckId)
                if (_deckWithCards.value?.cards.isNullOrEmpty()) {
                    _state.value = UIState.EMPTY
                } else {
                    _state.value = UIState.NORMAL
                }

            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }
        }
    }

}