package nl.recall.presentation.createCard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.recall.presentation.createCard.model.CreateCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.core.annotation.InjectedParam

class CreateCardViewModel(
    @InjectedParam private val args: CreateCardViewModelArgs
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> by lazy {
        _state.asStateFlow()
    }


}