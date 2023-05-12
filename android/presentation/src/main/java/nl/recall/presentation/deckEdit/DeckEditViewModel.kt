package nl.recall.presentation.deckEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDeckById
import nl.recall.domain.deck.UpdateDeck
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.deckEdit.model.DeckEditViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckEditViewModel(
    @InjectedParam private val args: DeckEditViewModelArgs,
    private val getDeckById: GetDeckById,
    private val updateDeck: UpdateDeck
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deck = MutableStateFlow<Deck?>(null)
    val deck: StateFlow<Deck?> by lazy {
        fetchDeck(args.id)
        _deck.asStateFlow()
    }

    private val _updatedDeckBoolean = MutableStateFlow(false)

    val updatedDeckBoolean: StateFlow<Boolean> =_updatedDeckBoolean.asStateFlow()

    private fun fetchDeck(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _deck.value = getDeckById.invoke(id).deck
            _state.value = UIState.NORMAL
        }
    }

    fun updateDeckInDatabase(deck: Deck) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UIState.LOADING
            _updatedDeckBoolean.value = updateDeck.invoke(deck)
            _state.value = UIState.NORMAL
        }
    }
}