package nl.recall.decksoverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.deck.DeckPreview
import nl.recall.destinations.DeckDetailScreenDestination
import nl.recall.domain.deck.model.Deck
import nl.recall.presentation.decksOverview.DecksOverviewViewModel
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun DecksOverviewSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: DecksOverviewViewModel = koinViewModel()
) {
    val navigateToDetail: () -> Unit = { navigator.navigate(DeckDetailScreenDestination) }

    val decks by viewModel.decks.collectAsState()
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                ),
                title = {
                    Text(text = stringResource(id = R.string.deck_searchbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                    }
                },
            )
        },
    ) { paddingValues ->
        Content(decks, navigateToDetail, paddingValues)
    }
}

@Composable
private fun Content(
    decks: Map<Deck, Int>,
    navigateToDetail: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 14.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            value = "",
            placeholder = {
                Text(
                    text = stringResource(R.string.search_bar_deck_hint),
                    color = AppTheme.neutral500
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = AppTheme.neutral200,
                cursorColor = Color.Black,
                disabledLabelColor = AppTheme.white,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {

            },
            shape = RoundedCornerShape(35.dp),
            singleLine = true,
            trailingIcon = {
                if (false) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "clear textfield"
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.padding(end = 6.dp),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(decks.entries.toList()) { deck ->
                DeckPreview(deck.key, cardCount = deck.value, onClick = {
                    navigateToDetail()
                })
            }
        }
    }
}