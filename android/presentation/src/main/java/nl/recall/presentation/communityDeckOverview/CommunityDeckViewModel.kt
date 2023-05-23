package nl.recall.presentation.communityDeckOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.communityDeck.GetCommunityDecks
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CommunityDeckViewModel(
	private val getCommunityDecks: GetCommunityDecks
): ViewModel() {
	private val _state = MutableStateFlow(UIState.LOADING)
	val state: StateFlow<UIState> by lazy {
		_state.asStateFlow()
	}

	private val _decks = MutableStateFlow<List<CommunityDeck>>(emptyList())
	val decks: StateFlow<List<CommunityDeck>> by lazy {
		fetchDecks()
		_decks.asStateFlow()
	}

	private fun fetchDecks() {
		_state.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			_decks.value = getCommunityDecks()

			if (_decks.value.isEmpty()) {
				_state.value = UIState.EMPTY
			} else {
				_state.value = UIState.NORMAL
			}
		}
	}

	fun searchDecks(title: String) {
		_state.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			if (title.isEmpty()) {
				_decks.value = getCommunityDecks()
			} else {
				_decks.value = getCommunityDecks(title)
			}

			if (_decks.value.isEmpty()) {
				_state.value = UIState.EMPTY
			} else {
				_state.value = UIState.NORMAL
			}
		}
	}
}