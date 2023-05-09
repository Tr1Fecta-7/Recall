package nl.recall.studyDeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.card.StudyCard
import nl.recall.errorScreen.ErrorScreen
import nl.recall.presentation.deckDetail.DeckDetailSearchScreenViewModel
import nl.recall.presentation.deckDetail.model.DeckDetailSearchScreenViewModelArgs
import nl.recall.presentation.studyDeck.StudyDeckViewModel
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun StudyDeckScreen(
    deckId: Long,
    navigator: DestinationsNavigator,
    viewModel: StudyDeckViewModel = koinViewModel(parameters = {
        parametersOf(StudyDeckViewModelArgs(deckId))
    })
) {

    val uiState by viewModel.state.collectAsState()

    when (uiState) {
        UIState.NORMAL -> {
            Content(navigator = navigator)
        }

        UIState.ERROR -> {
            ErrorScreen(
                titleText = stringResource(id = R.string.deck_detail_title_placeholder),
                errorText = stringResource(
                    id = R.string.get_deck_error
                ),
                navigator
            )
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

        UIState.EMPTY -> {

        }
    }
}

@Composable
private fun Content(
    navigator: DestinationsNavigator
) {

    var openDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppTheme.neutral50,
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.deck_detail_title_placeholder)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                }
            },

            )
    },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(AppTheme.neutral50)
            ) {
                StudyCard(

                )
            }

        }
    )
}