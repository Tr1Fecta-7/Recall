package nl.recall.createdeck
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
import nl.recall.components.AlertWindow
import nl.recall.components.ColorPickerWindow
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.createDeck.CreateDeckViewModel
import nl.recall.presentation.createDeck.model.CreateDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
private fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues){
    val viewModel: CreateDeckViewModel = koinViewModel(parameters = {
        parametersOf(CreateDeckViewModelArgs(1, "title","#2596be"))
    })
    val newDeck: UIState<Deck> by viewModel.deck.collectAsState()

    when(newDeck){
        is UIState.Success -> { DeckSuccess(newDeck, paddingValues) }
        else -> {}
    }


}

@Composable
private fun DeckSuccess(newDeck: UIState<Deck>, paddingValues: PaddingValues){
    var deckColor by remember {
        mutableStateOf(newDeck.data?.background_color)
    }

    var showAlert by remember{
        mutableStateOf(false)
    }

    var createTextField by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var iconTextField by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 60.dp)
                .weight(1f)
        ) {
            OutlinedTextField(
                value = createTextField,
                label = { Text(text = stringResource(id = R.string.create_deck_create_text_field))},
                onValueChange = { newText ->
                    createTextField = newText
                },
                placeholder = { Text(text = stringResource(id = R.string.create_deck_create_text_field_placeholder))},
            )

            Text(
                text = stringResource(id = R.string.create_deck_select_icon),
                modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 30.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(
                        Color(
                            deckColor?.toColorInt() ?: "white".toColorInt()
                        )
                    ),
           ) {
                TextField(
                    value = iconTextField,
                    onValueChange = { newText ->
                        if(newText.text.length <= 2 && newText.text.length % 2 == 0){
                            iconTextField = newText
                            newDeck.data?.icon = newText.text
                        }
                    },
                    modifier = Modifier.background(color = Color.White),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 155.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(deckColor?.toColorInt()?: "white".toColorInt()),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),

                    )
            }
            Button(onClick = {showAlert = true}) {
                Text(text = "select a color")
            }
            ColorPickerWindow(
                title = "Pick background color",
                subText = "",
                confirmText = "Select color",
                confirmTextColor = AppTheme.primary300,
                openDialog = true,
                onCloseDialog = { showAlert = false },
                onPressConfirm = {showAlert = false},
                onSelectColor = { deckColor = "#${it.hexCode}"}
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {

                },
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(md_theme_light_primary)
                    .width(300.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.create_deck_title),
                    color = Color.White
                )
            }
        }
    }
}