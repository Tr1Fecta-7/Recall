package nl.recall.presentation.decksOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDecksWithCardCount
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import java.util.Date

@KoinViewModel
class DecksOverviewViewModel(private val getDecksWithCardCount: GetDecksWithCardCount) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> by lazy {
        _state.asStateFlow()
    }

    private val _decks = MutableStateFlow<Map<Deck, Int>>(emptyMap())
    val decks: StateFlow<Map<Deck, Int>> by lazy {
        fetchDecks()
        _decks.asStateFlow()
    }

    private fun fetchDecks() {
        _state.value = UIState.LOADING

        // TODO: Remove sample data
        viewModelScope.launch {
            _decks.value = getDecksWithCardCount()

            if (_decks.value.isEmpty()) {
                _state.value = UIState.EMPTY
            } else {
                _state.value = UIState.NORMAL
            }
        }
    }
}