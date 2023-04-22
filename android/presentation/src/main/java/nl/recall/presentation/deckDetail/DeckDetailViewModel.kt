package nl.recall.presentation.deckDetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckDetailViewModel(
    @InjectedParam private val args: DeckDetailViewModelArgs
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.Loading<Deck>())
    val state: StateFlow<UIState<Deck>> = _state.asStateFlow()

    private val _deck = MutableStateFlow<Deck?>(null)
    val deck: StateFlow<Deck?> by lazy {
        _deck.asStateFlow()
    }
}