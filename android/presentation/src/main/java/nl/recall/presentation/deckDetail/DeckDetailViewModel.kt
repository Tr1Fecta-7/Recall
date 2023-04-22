package nl.recall.presentation.deckDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckDetailViewModel(
    @InjectedParam private val args: DeckDetailViewModelArgs
) : ViewModel() {

    private val _deck = MutableStateFlow<UIState<Deck>>(UIState.Loading())
    val deck: StateFlow<UIState<Deck>> by lazy {
        fetchDeck()
        _deck.asStateFlow()

    }

//    private val _deck = MutableStateFlow<Deck?>(null)
//    val deck: StateFlow<Deck?> by lazy {
//        this._deck.asStateFlow()
//
//    }

    private fun fetchDeck() {
        viewModelScope.launch {
            _deck.value = UIState.Success(Deck())
        }
    }
}