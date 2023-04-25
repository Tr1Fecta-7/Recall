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
    private val _deck = MutableStateFlow<UIState<Deck>>(UIState.Empty())
    val deck: StateFlow<UIState<Deck>> by lazy {
        fetchDeck()
        _deck.asStateFlow()
    }

    private fun fetchDeck(){
        viewModelScope.launch {
            _deck.value = UIState.Success(Deck(id = args.id, title = args.title, background_color = args.background_color))
        }
    }
}

