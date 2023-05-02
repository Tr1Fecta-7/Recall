package nl.recall.presentation.deckDetail

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
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckDetailViewModel(
    @InjectedParam private val args: DeckDetailViewModelArgs, private val getDeckById: GetDeckById
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deck = MutableStateFlow<DeckWithCards?>(null)
    val deck: StateFlow<DeckWithCards?> by lazy {
        fetchDeck()
        _deck.asStateFlow()
    }

    private fun fetchDeck() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = UIState.LOADING
                _deck.value = getDeckById(args.id)
                _state.value = UIState.NORMAL
            } catch (exception: Exception) {
                _state.value = UIState.ERROR
            }

        }
    }
}