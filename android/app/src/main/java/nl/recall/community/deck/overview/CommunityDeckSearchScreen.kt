package nl.recall.community.deck.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.ImageMessage
import nl.recall.components.communityDeck.CommunityDeckPreview
import nl.recall.destinations.CommunityDeckDetailScreenDestination
import nl.recall.presentation.community.deck.overview.CommunityDeckViewModel
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CommunityDeckSearchScreen(
	navController: NavController,
	navigator: DestinationsNavigator,
	viewModel: CommunityDeckViewModel = koinViewModel(),
) {
	val decks by viewModel.decks.collectAsState()
	val uiState by viewModel.state.collectAsState()

	Content(
		navController = navController,
		navigateBack = { navigator.popBackStack() },
		viewModel::searchDecks
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
					text = stringResource(id = R.string.community_deck_overview_error)
				)
			}
		}
	}
}

@Composable
private fun Content(
	navController: NavController,
	navigateBack: () -> Unit,
	searchDecks: (String) -> Unit,
	content: @Composable (() -> Unit),
) {
	var searchQuery by remember {
		mutableStateOf(TextFieldValue(String()))
	}
	val focusRequester = remember { FocusRequester() }

	Scaffold(
		containerColor = AppTheme.neutral50,
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.neutral50
				),
				title = {
					Text(text = stringResource(id = R.string.search_bar_community_hint))
				},
				navigationIcon = {
					IconButton(onClick = { navigateBack() }) {
						Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
					}
				},
			)
		},
		bottomBar = { BottomNav(navController = navController) }
	) { paddingValues ->
		Column(
			modifier = Modifier
				.padding(paddingValues)
				.padding(horizontal = 14.dp)
				.focusRequester(focusRequester)
				.onPlaced {
					focusRequester.requestFocus()
				},
		) {
			TextField(
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 8.dp),
				value = searchQuery,
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
					searchQuery = it
					searchDecks(searchQuery.text)
				},
				shape = RoundedCornerShape(35.dp),
				singleLine = true,
				trailingIcon = {
					if (searchQuery.text.isNotEmpty()) {
						IconButton(onClick = { searchQuery = TextFieldValue(String()) }) {
							Icon(
								imageVector = Icons.Outlined.Close,
								contentDescription = "clear text-field"
							)
						}
					} else {
						IconButton(
							modifier = Modifier.padding(end = 6.dp),
							onClick = { /*Do nothing*/ }) {
							Icon(
								imageVector = Icons.Default.Search,
								contentDescription = "search"
							)
						}
					}
				}
			)

			Spacer(modifier = Modifier.height(18.dp))

			Text(
				text = stringResource(id = R.string.most_downloaded_title),
				fontWeight = FontWeight.Bold
			)

			Spacer(modifier = Modifier.height(6.dp))

			content()
		}
	}
}