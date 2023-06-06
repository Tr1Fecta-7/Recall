package nl.recall.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.recall.domain.onboarding.SetOnboardingCompleted
import nl.recall.presentation.deck.overview.model.DeckOverviewNavigationAction
import nl.recall.presentation.onboarding.model.OnboardingNavigationAction
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class OnboardingViewModel(
    private val setOnboardingCompleted: SetOnboardingCompleted
) : ViewModel() {

    private val _navigation = MutableSharedFlow<OnboardingNavigationAction>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val navigation = _navigation.asSharedFlow()

    fun setOnboarding(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setOnboardingCompleted.invoke(completed = completed)
            emitToNav()
        }
    }

    fun emitToNav() {
        _navigation.tryEmit(OnboardingNavigationAction.OPEN_DECKOVERVIEW)
    }

}