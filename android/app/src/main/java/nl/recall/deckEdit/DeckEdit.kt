package nl.recall.deckEdit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.deck.DeckFrontEndComponent
import nl.recall.createdeck.DeckLoading
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.deckEdit.DeckEditViewModel
import nl.recall.presentation.deckEdit.model.DeckEditViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun DeckEdit(
    navigator: DestinationsNavigator,
    clickedDeckId: Long,
    viewModel: DeckEditViewModel =
        koinViewModel(parameters = { parametersOf(DeckEditViewModelArgs(clickedDeckId)) })
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.edit_deck_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                ),
            )
        },
        content = { MainContent(navigator = navigator, it, viewModel) }
    )
}

@Composable
private fun MainContent(
    navigator: DestinationsNavigator,
    paddingValues: PaddingValues,
    viewModel: DeckEditViewModel
){
    viewModel.observeDeck()
    val deck = viewModel.deck.collectAsState().value
    val uiState: UIState by viewModel.state.collectAsState()
    val updatedInDatabase = viewModel.updatedDeckBoolean.collectAsState()

    when(uiState) {
        UIState.NORMAL -> {
            deck?.let {

                if(updatedInDatabase.value) navigator.popBackStack()

                var deckColor by remember {
                    mutableStateOf(deck.color)
                }

                var showAlert by remember {
                    mutableStateOf(false)
                }

                var deckTitleTextField by remember {
                    mutableStateOf(TextFieldValue(deck.title))
                }
                var emojiTextfield by remember {
                    mutableStateOf(TextFieldValue(deck.icon))
                }
                var validationTitle by remember {
                    mutableStateOf(true)
                }

                var isEmojiFieldCorrect by remember {
                    //stardart emoji present, thus onload always true
                    mutableStateOf(true)
                }

                var characterCount by remember {
                    mutableStateOf(0)
                }

                DeckFrontEndComponent(
                    paddingValues = paddingValues,
                    onSubmitDeck = {
                        viewModel.updateDeckInDatabase(
                            Deck(
                                id = deck.id,
                                title = deckTitleTextField.text,
                                creationDate = deck.creationDate,
                                icon = emojiTextfield.text,
                                color = deckColor)
                        )
                    },
                    showAlert = showAlert,
                    toggleAlert = { showAlert = !showAlert },
                    preSelectedColor = deck.color,
                    onSetColor = { color -> deckColor = color },
                    deckTitleTextField = deckTitleTextField,
                    onDeckTextFieldValueChange = { text ->
                        deckTitleTextField = text
                        validationTitle = text.text.isNotBlank()
                    },
                    deckColor = deckColor,
                    emojiTextfield = emojiTextfield,
                    onEmojiTextFieldValueChange = { text ->
                        if (characterCount == 0 && text.text.isNotBlank()) {
                            emojiTextfield = text
                            characterCount++
                        } else if (text.text.isEmpty()) {
                            characterCount--
                            emojiTextfield = text
                        }
                    },
                    validationTitle = validationTitle,
                    validationEmoji = isEmojiFieldCorrect,
                    submitButtonText = stringResource(id = R.string.edit_deck_title)
                )
            }
        }

        UIState.LOADING -> {
            DeckLoading()
        }

        else -> {

        }
    }
}