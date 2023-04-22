package nl.recall.presentation.uiState

sealed class UIState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : UIState<T>(data)
    class Error<T>(message: String?, data: T? = null) : UIState<T>(data, message)
    class Loading<T> : UIState<T>()
    class Empty<T> : UIState<T>()
}