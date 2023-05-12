package nl.recall.presentation.createCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.SaveCard
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import java.util.Date

@KoinViewModel
class CreateCardViewModel(
    private val saveCard: SaveCard,
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.NORMAL)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _savedCardBoolean = MutableStateFlow(false)

    val savedCardBoolean: StateFlow<Boolean> = _savedCardBoolean.asStateFlow()


    fun saveCardToDatabase(front: String, back: String, dueDate: Date, deckId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UIState.LOADING
            _savedCardBoolean.value = saveCard.invoke(
                front = front,
                back = back,
                dueDate = dueDate,
                deckId = deckId
            )
            _state.value = UIState.NORMAL
        }
    }

}