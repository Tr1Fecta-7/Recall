package nl.recall.presentation.card.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.DeleteCard
import nl.recall.domain.deck.GetCardById
import nl.recall.domain.deck.UpdateCard
import nl.recall.domain.deck.model.Card
import nl.recall.presentation.card.edit.model.EditCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class EditCardViewModel(
    @InjectedParam private val args: EditCardViewModelArgs,
    private val getCardById: GetCardById,
    private val updateCard: UpdateCard,
    private val deleteCard: DeleteCard,
) : ViewModel() {

	private val _state = MutableStateFlow(UIState.LOADING)
	val state: StateFlow<UIState> by lazy {
		_state.asStateFlow()
	}

	private val _updatedCardBoolean = MutableStateFlow(false)
	val updatedCardBoolean: StateFlow<Boolean> by lazy {
		_updatedCardBoolean.asStateFlow()
	}

	private val _deletedCardBoolean = MutableStateFlow(false)
	val deletedCardBoolean: StateFlow<Boolean> by lazy {
		_deletedCardBoolean.asStateFlow()
	}

	private val _card = MutableStateFlow<Card?>(null)
	val card: StateFlow<Card?> by lazy {
		fetchCard(args.deckId, args.cardId)
		_card.asStateFlow()
	}

	private fun fetchCard(deckId: Long, cardId: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			_card.value = getCardById(deckId, cardId)
			_state.value = UIState.NORMAL
		}
	}

	fun updateCardInDatabase(card: Card) {
		viewModelScope.launch(Dispatchers.IO) {
			_state.value = UIState.LOADING
			_updatedCardBoolean.value = updateCard.invoke(card)
			_state.value = UIState.NORMAL
		}
	}

	fun deleteCardInDatabase(deckId: Long, cardId: Long) {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_state.value = UIState.LOADING
				_deletedCardBoolean.value = deleteCard(deckId, cardId)
				_state.value = UIState.NORMAL
			} catch (exception: Exception) {
				Log.e("ERROR IN EditCardViewModel", exception.toString())
				_state.value = UIState.ERROR
			}
		}
	}

}