package nl.recall.presentation.communityCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.communityDeck.models.CommunityCard
import nl.recall.presentation.communityCard.model.CommunityCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class CommunityCardViewModel(
	@InjectedParam private val args: CommunityCardViewModelArgs,
) : ViewModel() {

	private val _state = MutableStateFlow(UIState.LOADING)
	val state: StateFlow<UIState> = _state.asStateFlow()

	private val _communityCard = MutableStateFlow<CommunityCard?>(null)
	val communityCard: StateFlow<CommunityCard?> = _communityCard.asStateFlow()

	fun getCardById() {
		_state.value = UIState.LOADING
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_communityCard.value = CommunityCard(back = "back", front = "front", id = args.id)
				_state.value = UIState.NORMAL
			} catch (e: Exception) {
				_state.value = UIState.ERROR
			}
		}
	}
}