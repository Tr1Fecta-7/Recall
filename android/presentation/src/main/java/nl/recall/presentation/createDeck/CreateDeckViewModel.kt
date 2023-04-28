package nl.recall.presentation.createDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.SaveDeck
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.createDeck.model.CreateDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import java.util.Date

@KoinViewModel

class CreateDeckViewModel(@InjectedParam private val args: CreateDeckViewModelArgs, private val saveDeck: SaveDeck): ViewModel(){
    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _deck = MutableStateFlow<Deck?>(null)
    val deck: StateFlow<Deck?> by lazy{
        fetchDeck()
        _deck.asStateFlow()
    }

    private val _savedDeckBoolean = MutableStateFlow(false)

    val savedDeckBoolean: StateFlow<Boolean> by lazy {
        _savedDeckBoolean.asStateFlow()
    }


    private fun fetchDeck(){
        viewModelScope.launch {
            _deck.value = Deck(
                id = args.id,
                title = args.title,
                color = args.color,
                creationDate = args.creationDate,
                icon = args.icon
            )
            _state.value = UIState.NORMAL
        }
    }

    fun saveDeckToDatabase(title: String, creationDate: Date, icon: String, color: String) {
        viewModelScope.launch {
            _state.value = UIState.LOADING
            _savedDeckBoolean.value = saveDeck.invoke(title = title, creationDate = creationDate, icon = icon, color = color)
            _state.value = UIState.NORMAL
        }
    }
}

