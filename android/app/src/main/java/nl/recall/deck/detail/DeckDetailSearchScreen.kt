package nl.recall.deck.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.ImageMessage
import nl.recall.destinations.EditCardScreenDestination
import nl.recall.domain.deck.model.Card
import nl.recall.presentation.deck.detail.DeckDetailSearchScreenViewModel
import nl.recall.presentation.deck.detail.model.DeckDetailSearchScreenViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DeckDetailSearchScreen(
	navigator: DestinationsNavigator,
	deckId: Long,
	viewModel: DeckDetailSearchScreenViewModel = koinViewModel(parameters = {
		parametersOf(DeckDetailSearchScreenViewModelArgs(deckId))
	}),
) {
	val cards by viewModel.cards.collectAsState()
	val uiState by viewModel.state.collectAsState()
	var searchQuery by remember {
		mutableStateOf(TextFieldValue(""))
	}
	val navigateToCard: (Long) -> Unit =
		{
			navigator.navigate(EditCardScreenDestination(clickedCardId = it, deckId = deckId))
		}
	val focusRequester = remember { FocusRequester() }




	Scaffold(topBar = {
		TopAppBar(
			colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
				containerColor = AppTheme.neutral50
			),
			title = {
				Text(text = stringResource(id = R.string.card_searchbar_title))
			},
			navigationIcon = {
				IconButton(onClick = { navigator.popBackStack() }) {
					Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
				}
			},
		)
	}, containerColor = AppTheme.neutral50, content = { paddingValues ->


		Column(
			modifier = Modifier
				.padding(paddingValues)
				.padding(horizontal = 20.dp)
		) {
			TextField(modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 8.dp)
				.focusRequester(focusRequester)
				.onPlaced {
					focusRequester.requestFocus()
				}, value = searchQuery, placeholder = {
				Text(
					text = stringResource(R.string.search_bar_card_hint),
					color = AppTheme.neutral500
				)
			}, colors = TextFieldDefaults.textFieldColors(
				containerColor = AppTheme.neutral200,
				cursorColor = Color.Black,
				disabledLabelColor = AppTheme.white,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent
			), onValueChange = {
				searchQuery = it
				viewModel.searchDecks(it.text)
			}, shape = RoundedCornerShape(35.dp), singleLine = true, trailingIcon = {
				if (searchQuery.text.isNotEmpty()) {
					IconButton(onClick = {
						searchQuery = TextFieldValue(String())
						viewModel.searchDecks(searchQuery.text)
					}) {
						Icon(
							imageVector = Icons.Outlined.Close,
							contentDescription = "clear text field"
						)
					}
				} else {
					IconButton(modifier = Modifier.padding(end = 6.dp),
						onClick = { viewModel.searchDecks(searchQuery.text) }) {
						Icon(
							imageVector = Icons.Default.Search,
							contentDescription = "search"
						)
					}
				}
			})

			when (uiState) {
				UIState.NORMAL -> {
					SearchResults(cards, onClick = {
						navigateToCard(it)
					})
				}

				UIState.ERROR -> {
					ImageMessage(
						painter = painterResource(id = R.drawable.error_image),
						text = stringResource(id = R.string.no_cards_found)
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
					ImageMessage(
						painter = painterResource(id = R.drawable.no_decks_found),
						text = stringResource(id = R.string.no_cards_found)
					)

				}
			}
		}
	}


	)


}

@Composable
fun SearchResults(cards: List<Card>, onClick: (Long) -> (Unit)) {
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(2.dp)
	) {
		item { Spacer(modifier = Modifier.padding(top = 10.dp)) }
		itemsIndexed(items = cards, itemContent = { index, card ->
			val state = remember {
				MutableTransitionState(false).apply {
					targetState = true
				}
			}

			AnimatedVisibility(
				visibleState = state, enter = slideInVertically(
					initialOffsetY = { it + 20 }, animationSpec = tween(
						durationMillis = (index * 100)
					)
				), exit = fadeOut()
			) {
				Card(
					onClick = { onClick(card.id) },
					shape = RoundedCornerShape(12.dp),
					border = BorderStroke(1.dp, AppTheme.neutral200),
					modifier = Modifier.fillMaxWidth()
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.SpaceBetween,
						modifier = Modifier
							.background(AppTheme.white)
							.padding(horizontal = 16.dp, vertical = 8.dp)
							.fillMaxWidth()
					) {
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.spacedBy(16.dp),
						) {
							Text(
								text = card.front,
								color = AppTheme.neutral800,
								style = MaterialTheme.typography.titleMedium,
							)
						}
						Icon(
							painter = painterResource(id = R.drawable.baseline_chevron_right_24),
							contentDescription = "arrow right",
							tint = AppTheme.neutral800
						)
					}
				}
			}
		})
	}
}