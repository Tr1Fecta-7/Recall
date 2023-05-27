package nl.recall.communityDecksOverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.ImageMessage
import nl.recall.components.communityDeck.CommunityDeckPreview
import nl.recall.destinations.CommunityDeckDetailScreenDestination
import nl.recall.destinations.CommunityDeckSearchScreenDestination
import nl.recall.presentation.communityDeckOverview.CommunityDeckViewModel
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CommunityDeckOverviewScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: CommunityDeckViewModel = koinViewModel()
) {
    val decks by viewModel.decks.collectAsState()
    val uiState by viewModel.state.collectAsState()

    Content(
        navigateToDeckSearch = {
            navigator.navigate(CommunityDeckSearchScreenDestination)
        },
        navController = navController,
    ) {
        when (uiState) {
            UIState.NORMAL -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(decks) { deck ->
                        CommunityDeckPreview(deck, onClick = {
                            navigator.navigate(CommunityDeckDetailScreenDestination(deckId = deck.id))
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
    }
}

@Composable
private fun Content(
    navigateToDeckSearch: () -> Unit,
    navController: NavController,
    content: @Composable (() -> Unit)
) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        bottomBar = { BottomNav(navController = navController) }
    ) { paddingValues ->
        Column(
            Modifier.padding(
                top = paddingValues.calculateTopPadding() + 56.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 14.dp,
                end = 14.dp
            )
        ) {
            Text(
                text = stringResource(id = R.string.community_deck_overview_title),
                color = AppTheme.neutral800,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(id = R.string.community_deck_overview_subtitle),
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
                }
            ) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.search_bar_community_hint),
                        color = AppTheme.neutral500
                    )
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }
            }

            Text(
                text = stringResource(id = R.string.most_downloaded_title),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            content()
        }
    }
}
