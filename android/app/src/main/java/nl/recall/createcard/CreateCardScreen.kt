package nl.recall.createcard

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
import nl.recall.destinations.CreateCardScreenDestination
import nl.recall.domain.models.CardPreviewData
import nl.recall.presentation.createCard.CreateCardViewModel
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Destination
@Composable
fun CreateCardScreen(navController: NavController, navigator: DestinationsNavigator, deckId: Long) {
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
        bottomBar = { BottomNav(navController = navController) },
        content = { MainContent(navController = navController, navigator = navigator, it, deckId = deckId) }
    )
}

@Composable
fun MainContent(navController: NavController, navigator: DestinationsNavigator, paddingValues: PaddingValues, deckId: Long) {
    val viewModel: CreateCardViewModel = koinViewModel()

    val uiState: UIState by viewModel.state.collectAsState()
    val savedCardInDatabase = viewModel.savedCardBoolean.collectAsState().value;

    when (uiState) {
        UIState.NORMAL -> {
            if (savedCardInDatabase) {
                navController.popBackStack()
                navController.navigate(CreateCardScreenDestination(deckId).route)
                return
            }

            val card = CardPreviewData(
                front = "",
                back = "",
                buttonText = stringResource(id = R.string.create_card_title)
            )

            CardPreview(card, paddingValues, onClick = {
                viewModel.saveCardToDatabase(front = card.front, back = card.back, dueDate = Date(), deckId = deckId)
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
        UIState.ERROR -> {

        }
        UIState.EMPTY -> {

        }
    }


}