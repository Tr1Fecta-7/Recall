package nl.recall.presentation.communityDeckDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.communityDeck.GetCommunityDeckById
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.presentation.communityDeckDetail.model.CommunityDeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class CommunityDeckDetailViewModel(
    @InjectedParam private val args: CommunityDeckDetailViewModelArgs,
    private val getCommunityDeckById: GetCommunityDeckById,
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _communityDeck = MutableStateFlow<CommunityDeck?>(null)
    val communityDeck: StateFlow<CommunityDeck?> = _communityDeck.asStateFlow()

    fun getDeckById() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UIState.LOADING
            try {
            	_communityDeck.value = getCommunityDeckById(args.id)

                _state.value = UIState.NORMAL
            } catch (e: Exception) {
                _state.value = UIState.ERROR
            }
        }
    }
}