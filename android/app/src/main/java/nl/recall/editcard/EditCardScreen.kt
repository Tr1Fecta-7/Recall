package nl.recall.editcard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.AlertWindow
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
    val viewModel: EditCardViewModel = koinViewModel(parameters = {
        parametersOf(EditCardViewModelArgs(deckId = deckId, cardId = clickedCardId))
    })
    var openDialog by remember { mutableStateOf(false) }
    var closeDialog: () -> Unit = { openDialog = false }

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
                actions = {
                    IconButton(onClick = {
                        openDialog = true
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "deleteCard")
                    }
                }
            )
        },
        bottomBar = { BottomNav(navController = navController) },
        content = { MainContent(navigator = navigator, it, viewModel, deckId, openDialog, closeDialog) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues,
                viewModel: EditCardViewModel, deckId: Long, openDialog: Boolean,
                closeDialog: () -> (Unit)
) {
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

                AlertWindow(
                    title = stringResource(id = R.string.dialog_delete_card_title),
                    subText = stringResource(id = R.string.dialog_delete_card_text),
                    confirmText = stringResource(id = R.string.delete_text),
                    confirmTextColor = AppTheme.red700,
                    openDialog = openDialog,
                    onCloseDialog = { closeDialog() },
                    onPressConfirm = {
                        viewModel.deleteCardInDatabase(deckId = deckId, cardId = card.id)
                    }
                )
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
