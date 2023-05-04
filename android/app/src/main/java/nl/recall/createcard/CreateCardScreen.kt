package nl.recall.createcard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.card.CardPreview
import nl.recall.destinations.DeckDetailScreenDestination
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.domain.models.CardPreviewData
import nl.recall.presentation.createCard.CreateCardViewModel
import nl.recall.presentation.createCard.model.CreateCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Destination
@Composable
fun CreateCardScreen(navigator: DestinationsNavigator) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppTheme.neutral50),
                title = { Text(stringResource(id = R.string.create_card_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        content = { MainContent(navigator = navigator, it) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues) {
    val viewModel: CreateCardViewModel = koinViewModel(parameters = {
        parametersOf(CreateCardViewModelArgs(id = 1, front = "", back = "", dueDate = Date(), deckId = 1))
    })

    val uiState: UIState by viewModel.state.collectAsState()
    val savedCardInDatabase = viewModel.savedCardBoolean.collectAsState().value;

    when (uiState) {
        UIState.NORMAL -> {
            if (savedCardInDatabase) {
                // navigator.navigate() NAVIGATE BACK to the correct deck
                Log.d("DGN", "Saving worked")
                return
            }

            val card = CardPreviewData(
                front = "",
                back = "",
                buttonText = stringResource(id = R.string.create_card_title)
            )

            CardPreview(card, paddingValues, onClick = {
                viewModel.saveCardToDatabase(front = card.front, back = card.back, dueDate = Date(), deckId = 1)
            })
        }
        UIState.LOADING -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {

        }
    }


}