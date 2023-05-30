package nl.recall.presentation.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.recall.presentation.settings.model.AlgorithmStrength
import nl.recall.presentation.uiState.UIState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SettingsViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _strength = MutableStateFlow(AlgorithmStrength.NORMAL)
    val strength: StateFlow<AlgorithmStrength> = _strength.asStateFlow()

    fun updateStrength(algorithmStrength: AlgorithmStrength): Boolean {
        _strength.value = algorithmStrength
        return true
    }


}