package nl.recall.deck.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.deck.DeckFrontEndComponent
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.presentation.deck.create.CreateDeckViewModel
import nl.recall.presentation.deck.create.model.CreateDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Destination
@Composable
fun DeckCreate(navController: NavController, navigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.create_deck_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.neutral50
                ),
            )
        },
        bottomBar = { BottomNav(navController = navController) },
        content = { MainContent(navigator = navigator, it) }
    )
}

@Composable
private fun MainContent(
    navigator: DestinationsNavigator,
    paddingValues: PaddingValues,
    viewModel: CreateDeckViewModel = koinViewModel(parameters = {
        parametersOf(
            CreateDeckViewModelArgs(
                id = 1,
                title = "",
                color = "#2596be",
                creationDate = Date(),
                icon = "\uD83D\uDD25"
            )
        )
    }),
) {
    val deck = viewModel.deck.collectAsState().value
    val uiState: UIState by viewModel.state.collectAsState()
    val savedDeckIntoDatabase = viewModel.savedDeckBoolean.collectAsState().value;
    if (savedDeckIntoDatabase) navigator.navigate(DecksOverviewScreenDestination) else {
        when (uiState) {
            UIState.NORMAL -> {
                deck?.let {


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
                        mutableStateOf(false)
                    }

                    var validationEmoji by remember {
                        // stardart emoji present, thus onload always true
                        mutableStateOf(true)
                    }

                    var characterCount by remember {
                        mutableStateOf(0)
                    }

                    DeckFrontEndComponent(
                        paddingValues = paddingValues,
                        onSubmitDeck = {
                            viewModel.saveDeckToDatabase(
                                title = deckTitleTextField.text,
                                creationDate = Date(),
                                icon = emojiTextfield.text,
                                color = deckColor
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
                        validationEmoji = validationEmoji,
                        submitButtonText = stringResource(id = R.string.create_deck_title)
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

}

@Composable
fun DeckLoading() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
    CircularProgressIndicator()
}