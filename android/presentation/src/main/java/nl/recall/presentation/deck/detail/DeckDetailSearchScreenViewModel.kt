package nl.recall.presentation.deck.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetCardsBySearchQuery
import nl.recall.domain.deck.model.Card
import nl.recall.presentation.deck.detail.model.DeckDetailSearchScreenViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckDetailSearchScreenViewModel(
    @InjectedParam private val args: DeckDetailSearchScreenViewModelArgs,
    private val getCardsBySearchQuery: GetCardsBySearchQuery,
) : ViewModel() {

	private val _state = MutableStateFlow(UIState.EMPTY)
	val state: StateFlow<UIState> = _state.asStateFlow()

	private val _cards = MutableStateFlow<List<Card>>(listOf())
	val cards: StateFlow<List<Card>> by lazy {
		searchDecks("")
		_cards.asStateFlow()
	}

	fun searchDecks(query: String) {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_state.value = UIState.LOADING
				_cards.value = getCardsBySearchQuery(args.id, query)
				if (_cards.value.isEmpty()) {
					_state.value = UIState.EMPTY
				} else {
					_state.value = UIState.NORMAL
				}
			} catch (e: Exception) {
				println(e)
				_state.value = UIState.ERROR
			}
		}
	}
}