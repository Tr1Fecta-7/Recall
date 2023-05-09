package nl.recall.createdeck
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.ColorPickerWindow
import nl.recall.components.deck.DeckFrontEndComponent
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.presentation.createDeck.CreateDeckViewModel
import nl.recall.presentation.createDeck.model.CreateDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Destination
@Composable
fun DeckCreate(navigator: DestinationsNavigator){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(stringResource(id = R.string.create_deck_title))},
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
        content = { MainContent(navigator = navigator, it)}
    )
}

@Composable
private fun MainContent(
    navigator: DestinationsNavigator,
    paddingValues: PaddingValues,
    viewModel: CreateDeckViewModel = koinViewModel(parameters = { parametersOf(CreateDeckViewModelArgs(id = 1, title = "", color = "#2596be", creationDate = Date(), icon = "\uD83D\uDD25")) })
){
    val deck = viewModel.deck.collectAsState().value
    val uiState: UIState by viewModel.state.collectAsState()
    val savedDeckIntoDatabase = viewModel.savedDeckBoolean.collectAsState().value;

    when(uiState){
        UIState.NORMAL -> {
            deck?.let {

                if(savedDeckIntoDatabase) navigator.navigate(DecksOverviewScreenDestination)

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
                    //stardart emoji present, thus onload always true
                    mutableStateOf(true)
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
                    onDeckTextFieldValueChange = {text ->
                        deckTitleTextField = text
                        validationTitle = text.text.isNotBlank()
                    },
                    deckColor = deckColor,
                    emojiTextfield = emojiTextfield,
                    onEmojiTextFieldValueChange = { text ->
                        if (text.text.length <= 2 && text.text.length % 2 == 0) {
                            emojiTextfield = text
                            validationEmoji = text.text.isNotBlank()
                        } else {
                            validationEmoji = false
                        }
                    },
                    validationTitle = validationTitle,
                    validationEmoji = validationEmoji,
                )
            }
        }
        UIState.LOADING -> { DeckLoading() }
        else -> {

        }
    }

}

@Composable
fun DeckLoading(){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }
    CircularProgressIndicator()
}