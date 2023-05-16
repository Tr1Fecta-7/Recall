package nl.recall.presentation.deckDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.recall.domain.deck.DeleteDeck
import nl.recall.domain.deck.ObserveDeckById
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DeckDetailViewModel(
    @InjectedParam private val args: DeckDetailViewModelArgs, private val observeDeckById: ObserveDeckById, private val deleteDeck: DeleteDeck
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deck = MutableStateFlow<DeckWithCards?>(null)
    val deck: StateFlow<DeckWithCards?> = _deck.asStateFlow()

    fun observeDeck() {
        viewModelScope.launch(Dispatchers.IO) {
            observeDeckById(args.id).collectLatest { deckWithCards ->
                try {
                    _state.value = UIState.LOADING
                    _deck.value = deckWithCards
                    _state.value = UIState.NORMAL
                } catch (exception: Exception) {
                    _state.value = UIState.ERROR
                }
            }
        }
    }

    private val _isDeckDeleted = MutableStateFlow(false)

    val isDeckDeleted: StateFlow<Boolean> by lazy {
        _isDeckDeleted.asStateFlow()
    }

    fun deleteDeckById(deckWithCards: DeckWithCards){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _state.value = UIState.LOADING
                _isDeckDeleted.value = deleteDeck(deckWithCards)
                _state.value = UIState.NORMAL
            } catch(exception: Exception){
                Log.e("ERROR IN DECKDETAILVIEWMODEL", exception.toString())
                _state.value = UIState.ERROR
            }
        }
    }
}