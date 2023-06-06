package nl.recall.presentation.deck.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetDecksWithCardCount
import nl.recall.domain.deck.ObserveDecksWithCardCount
import nl.recall.domain.deck.SearchDeckWithCardCount
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.onboarding.GetOnboardingCompleted
import nl.recall.domain.onboarding.SetOnboardingCompleted
import nl.recall.presentation.deck.overview.model.DeckOverviewNavigationAction
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DecksOverviewViewModel(
	private val getDecksWithCardCount: GetDecksWithCardCount,
	private val searchDecksWithCardCount: SearchDeckWithCardCount,
	private val observeDecksWithCardCount: ObserveDecksWithCardCount,
	private val getOnboardingCompleted: GetOnboardingCompleted,
) : ViewModel() {

	private val _navigation = MutableSharedFlow<DeckOverviewNavigationAction>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
	val navigation = _navigation.asSharedFlow()

	private val _state = MutableStateFlow(UIState.LOADING)
	val state: StateFlow<UIState> by lazy {
		_state.asStateFlow()
	}

	private val _decks = MutableStateFlow<Map<Deck, Int>>(emptyMap())
	val decks: StateFlow<Map<Deck, Int>> by lazy {
		observeDecks()
		_decks.asStateFlow()
	}

	fun checkAndNavigateOnboarding() {
		val completed = getOnboardingCompleted.invoke()
		if (!completed) {
			emitToNav()
		}
	}

	fun observeDecks() {
		viewModelScope.launch(Dispatchers.IO) {
			observeDecksWithCardCount().catch {
				_state.value = UIState.ERROR
			}.collectLatest { deckWithCount ->
				_decks.value = deckWithCount

				if (deckWithCount.isEmpty()) {
					_state.value = UIState.EMPTY
				} else {
					_state.value = UIState.NORMAL
				}
			}
		}
	}

	fun emitToNav() {
		_navigation.tryEmit(DeckOverviewNavigationAction.OPEN_ONBOARDING)
	}

	fun searchDecks(title: String) {
		_state.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			if (title.isEmpty()) {
				_decks.value = getDecksWithCardCount()
			} else {
				_decks.value = searchDecksWithCardCount(title)
			}

			if (_decks.value.isEmpty()) {
				_state.value = UIState.EMPTY
			} else {
				_state.value = UIState.NORMAL
			}
		}
	}
}