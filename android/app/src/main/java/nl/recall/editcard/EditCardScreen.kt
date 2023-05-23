package nl.recall.editcard

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.card.CardPreview
import nl.recall.createcard.MainContent
import nl.recall.domain.deck.model.Card
import nl.recall.domain.models.CardPreviewData
import nl.recall.presentation.editCard.EditCardViewModel
import nl.recall.presentation.editCard.model.EditCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Destination
@Composable
fun EditCardScreen(navController: NavController, navigator: DestinationsNavigator,
                   clickedCardId: Long, deckId: Long) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppTheme.neutral50),
                title = { Text(stringResource(id = R.string.edit_card_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        bottomBar = { BottomNav(navController = navController) },
        content = { MainContent(navigator = navigator, it, clickedCardId, deckId) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues,
                clickedCardId: Long, deckId: Long) {
    val viewModel: EditCardViewModel = koinViewModel(parameters = {
        parametersOf(EditCardViewModelArgs(deckId = deckId, cardId = clickedCardId))
    })

    val card = viewModel.card.collectAsState().value
    val uiState: UIState by viewModel.state.collectAsState()
    val updatedCardInDatabase = viewModel.updatedCardBoolean.collectAsState().value;
    val deletedCardInDatabase = viewModel.deletedCardBoolean.collectAsState().value;


    when(uiState) {
        UIState.NORMAL -> {
            card?.let {
                if (updatedCardInDatabase || deletedCardInDatabase) {
                    navigator.popBackStack()
                }

                val cardData = CardPreviewData(
                    front = card.front,
                    back = card.back,
                    buttonText = stringResource(id = R.string.edit_card_title)
                )

                CardPreview(card = cardData, paddingValues = paddingValues, onClick = {
                    viewModel.updateCardInDatabase(
                        Card(
                            id = card.id,
                            front = cardData.front,
                            back = cardData.back,
                            dueDate = Date(),
                            deckId = deckId
                        )
                    )
                })
            }
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
        else -> {}
    }


}
