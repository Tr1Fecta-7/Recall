package nl.recall.decksoverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.deck.DeckPreview
import nl.recall.destinations.DeckCreateDestination
import nl.recall.destinations.DeckDetailScreenDestination
import nl.recall.destinations.DecksOverviewSearchScreenDestination
import nl.recall.domain.models.DeckPreviewData
import nl.recall.theme.AppTheme

@RootNavGraph(start = true)
@Destination
@Composable
fun DecksOverviewScreen(navigator: DestinationsNavigator) {
    val navigateToDetail: () -> Unit = { navigator.navigate(DeckDetailScreenDestination) }
    val navigateToCreateDeck: () -> Unit = { navigator.navigate(DeckCreateDestination) }
    val navigateToDeckSearch: () -> Unit = {
        navigator.navigate(DecksOverviewSearchScreenDestination)
    }

    val decks = listOf(
        DeckPreviewData(
            title = "Mandarin HSK 1",
            cardCount = 512,
            backgroundColor = "#FFEAEA", // Color("#FFEAEA".toColorInt()),
            emoji = "🇨🇳",
        ),
        DeckPreviewData(
            title = "Maths L1 & L2",
            cardCount = 256,
            backgroundColor = "#FFEAB6", // Color("#FFEAB6".toColorInt()),
            emoji = "🧮",
        )
    )

    Content(decks, navigateToDetail, navigateToCreateDeck, navigateToDeckSearch)
}

@Composable
private fun Content(
    decks: List<DeckPreviewData>,
    navigateToDetail: () -> Unit,
    navigateToCreateDeck: () -> Unit,
    navigateToDeckSearch: () -> Unit
) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToCreateDeck() },
                contentColor = AppTheme.primary900,
                containerColor = AppTheme.primary300
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
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
                        text = stringResource(R.string.search_bar_deck_hint),
                        color = AppTheme.neutral500
                    )
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(decks) { deck ->
                    DeckPreview(deck, onClick = {
                        navigateToDetail()
                    })
                }
            }
        }
    }
}