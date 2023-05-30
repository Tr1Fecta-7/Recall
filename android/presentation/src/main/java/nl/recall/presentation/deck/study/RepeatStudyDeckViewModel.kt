package nl.recall.presentation.deck.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetShuffledDeckById
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.deck.study.model.StudyDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class RepeatStudyDeckViewModel(
	@InjectedParam private val args: StudyDeckViewModelArgs,
	private val getShuffledDeckById: GetShuffledDeckById,
) : ViewModel() {

	private val _state = MutableStateFlow(UIState.EMPTY)
	val state: StateFlow<UIState> = _state.asStateFlow()

	private val _deckWithCards = MutableStateFlow<DeckWithCards?>(null)
	val deckWithCards: StateFlow<DeckWithCards?> by lazy {
		getDeckWithCards()
		_deckWithCards.asStateFlow()
	}

	private val _progress = MutableStateFlow(0.0000f)
	val progress: StateFlow<Float> = _progress.asStateFlow()

	private val _iterator = MutableStateFlow(0)
	val iterator: StateFlow<Int> = _iterator.asStateFlow()

	private fun getDeckWithCards() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_state.value = UIState.LOADING
				_deckWithCards.value = getShuffledDeckById(args.deckId)
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

	fun onSwipeCard() {
		_iterator.value++
		_deckWithCards.value?.let {
			_progress.value = (iterator.value.toFloat() / it.cards.size.toFloat())
		}
	}
}