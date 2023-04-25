package nl.recall.presentation.createDeck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.createDeck.model.CreateDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel

class CreateDeckViewModel(@InjectedParam private val args: CreateDeckViewModelArgs): ViewModel(){
    private val _state = MutableStateFlow(UIState.EMPTY)
    val state: StateFlow<UIState> by lazy {
        _state.asStateFlow()
    }

    private val _deck = MutableStateFlow<Deck>(Deck())
    val deck: StateFlow<Deck> by lazy{
        fetchDeck()
        _deck.asStateFlow()
    }

    private fun fetchDeck(){
        viewModelScope.launch {
            _deck.value = Deck(id = args.id, title = args.title, background_color = args.background_color)
            _state.value = UIState.NORMAL
        }
    }
}

