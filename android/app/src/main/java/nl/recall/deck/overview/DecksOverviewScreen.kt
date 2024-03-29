package nl.recall.deck.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.ImageMessage
import nl.recall.components.deck.DeckPreview
import nl.recall.destinations.DeckCreateDestination
import nl.recall.destinations.DeckDetailScreenDestination
import nl.recall.destinations.DecksOverviewSearchScreenDestination
import nl.recall.destinations.OnboardingScreenDestination
import nl.recall.presentation.deck.overview.DecksOverviewViewModel
import nl.recall.presentation.deck.overview.model.DeckOverviewNavigationAction
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun DecksOverviewScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: DecksOverviewViewModel = koinViewModel(),
) {
	LaunchedEffect(Unit) {
		viewModel.navigation.collect {
			when(it) {
				DeckOverviewNavigationAction.OPEN_ONBOARDING -> navigator.navigate(OnboardingScreenDestination)
            }
		}
	}

	val decks by viewModel.decks.collectAsState()
	val uiState by viewModel.state.collectAsState()

    Content(
        navigateToCreateDeck = { navigator.navigate(DeckCreateDestination) },
        navigateToDeckSearch = {
            navigator.navigate(DecksOverviewSearchScreenDestination)
        },
        navController = navController,
    ) {
        when (uiState) {
            UIState.NORMAL -> {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    decks.entries.forEach { entry ->
                        DeckPreview(entry.key, cardCount = entry.value, onClick = {
                            navigator.navigate(DeckDetailScreenDestination(deckId = entry.key.id))
                        })
                    }
                }
            }

            UIState.EMPTY -> {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    ImageMessage(
                        painter = painterResource(id = R.drawable.no_decks_found),
                        text = stringResource(id = R.string.no_decks_found)
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

            UIState.ERROR -> {
                ImageMessage(
                    painter = painterResource(id = R.drawable.error_image),
                    text = stringResource(id = R.string.no_decks_found)
                )
            }
        }

        viewModel.checkAndNavigateOnboarding()
    }
}

@Composable
private fun Content(
    navigateToCreateDeck: () -> Unit,
    navigateToDeckSearch: () -> Unit,
    navController: NavController,
    content: @Composable (() -> (Unit)),
) {
    val scrollState = rememberScrollState()

    Scaffold(containerColor = AppTheme.neutral50, floatingActionButton = {
        FloatingActionButton(
            onClick = { navigateToCreateDeck() },
            contentColor = AppTheme.primary900,
            containerColor = AppTheme.primary300
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }, bottomBar = { BottomNav(navController = navController) }) { paddingValues ->
        Column(
            Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 14.dp,
                    end = 14.dp
                )
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = stringResource(id = R.string.deck_overview_title),
                color = AppTheme.neutral800,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(id = R.string.deck_overview_subtitle),
                color = AppTheme.neutral500
            )

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.neutral200,
                ),
                shape = RoundedCornerShape(35.dp),
                onClick = {
                    navigateToDeckSearch()
                }) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.search_bar_deck_hint),
                        color = AppTheme.neutral500
                    )
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }

            }
            content()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
