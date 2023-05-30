package nl.recall.presentation.community.deck.detail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.communityDeck.GetCommunityDeckById
import nl.recall.domain.communityDeck.UpdateCommunityDeck
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.domain.deck.SaveCard
import nl.recall.domain.deck.SaveDeckAndGetId
import nl.recall.presentation.community.deck.detail.model.CommunityDeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import java.text.SimpleDateFormat
import java.util.Date

@KoinViewModel
class CommunityDeckDetailViewModel(
	@InjectedParam private val args: CommunityDeckDetailViewModelArgs,
	private val getCommunityDeckById: GetCommunityDeckById,
	private val saveDeckAndGetId: SaveDeckAndGetId,
	private val updateDeck: UpdateCommunityDeck,
	private val saveCard: SaveCard,
) : ViewModel() {

	private val _state = MutableStateFlow(UIState.LOADING)
	val state: StateFlow<UIState> = _state.asStateFlow()

	private val _importState = MutableStateFlow(UIState.EMPTY)
	val importState: StateFlow<UIState> = _importState.asStateFlow()

	private val _communityDeck = MutableStateFlow<CommunityDeck?>(null)
	val communityDeck: StateFlow<CommunityDeck?> = _communityDeck.asStateFlow()

	fun getDeckById() {
		_state.value = UIState.LOADING
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_communityDeck.value = getCommunityDeckById(args.id)
				_state.value = UIState.NORMAL
			} catch (e: Exception) {
				_state.value = UIState.ERROR
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	fun importCommunityDeck(communityDeck: CommunityDeck) {
		_importState.value = UIState.LOADING
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val formatter = SimpleDateFormat("yyyy-MM-dd")
				val communityDeckDate = formatter.parse(communityDeck.creation)

				updateDeck(
					communityDeck.copy(
						downloads = communityDeck.downloads + 1
					)
				)

				val deckId = saveDeckAndGetId(
					title = communityDeck.title,
					creationDate = communityDeckDate ?: Date(),
					icon = communityDeck.icon,
					color = communityDeck.color
				)

				communityDeck.cards.forEach { card ->
					saveCard(
						front = card.front,
						back = card.back,
						dueDate = Date(),
						deckId = deckId
					)
				}
				_importState.value = UIState.NORMAL
			} catch (e: Exception) {
				_importState.value = UIState.ERROR
			}
		}
	}
}