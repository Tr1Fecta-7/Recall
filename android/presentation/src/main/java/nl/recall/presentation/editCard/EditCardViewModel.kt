package nl.recall.presentation.editCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.GetCardById
import nl.recall.domain.deck.UpdateCard
import nl.recall.domain.deck.model.Card
import nl.recall.presentation.editCard.model.EditCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import java.lang.Thread.State

@KoinViewModel
class EditCardViewModel(
    @InjectedParam private val args: EditCardViewModelArgs,
    private val getCardById: GetCardById,
    private val updateCard: UpdateCard
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> by lazy {
        _state.asStateFlow()
    }

    private val _updatedCardBoolean = MutableStateFlow(false)
    val updatedCardBoolean: StateFlow<Boolean> by lazy {
        _updatedCardBoolean.asStateFlow()
    }

    private val _card = MutableStateFlow<Card?>(null)
    val card: StateFlow<Card?> by lazy {
        fetchCard(args.deckId, args.cardId)
        _card.asStateFlow()
    }

    private fun fetchCard(deckId: Long, cardId: Long){
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


}