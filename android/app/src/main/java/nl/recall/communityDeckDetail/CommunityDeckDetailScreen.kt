package nl.recall.communityDeckDetail

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.AlertWindow
import nl.recall.components.BottomNav
import nl.recall.components.ImageMessage
import nl.recall.components.communityDeck.CommunityDeckDetailStat
import nl.recall.domain.communityDeck.models.CommunityDeck
import nl.recall.presentation.communityDeckDetail.CommunityDeckDetailViewModel
import nl.recall.presentation.communityDeckDetail.model.CommunityDeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun CommunityDeckDetailScreen(
	deckId: Long,
	navController: NavController,
	navigator: DestinationsNavigator,
	viewModel: CommunityDeckDetailViewModel = koinViewModel(parameters = {
		parametersOf(CommunityDeckDetailViewModelArgs(deckId))
	}),
) {
	viewModel.getDeckById()
	val uiState by viewModel.state.collectAsState()
	val importState by viewModel.importState.collectAsState()
	val communityDeck by viewModel.communityDeck.collectAsState()
	val context = LocalContext.current

	when (importState) {
		UIState.NORMAL -> informUser(context, stringResource(R.string.deck_import_success))
		UIState.ERROR -> informUser(context, stringResource(R.string.deck_import_error))
		UIState.EMPTY, UIState.LOADING -> {}
	}

	when (uiState) {
		UIState.NORMAL -> {
			communityDeck?.let {
				Content(
					navController = navController,
					navigator = navigator,
					communityDeck = it,
					viewModel = viewModel
				)
			}
		}

		UIState.ERROR -> {
			Column(
				Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				ImageMessage(
					painter = painterResource(id = R.drawable.error_image),
					text = stringResource(id = R.string.no_deck_found)
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

		UIState.EMPTY -> {
			ImageMessage(
				painter = painterResource(id = R.drawable.no_decks_found),
				text = stringResource(id = R.string.no_deck_found)
			)
		}
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Content(
	navController: NavController,
	navigator: DestinationsNavigator,
	communityDeck: CommunityDeck,
	viewModel: CommunityDeckDetailViewModel,
) {
	val state = remember {
		MutableTransitionState(false).apply {
			targetState = true
		}
	}
	var isDialogOpen by remember { mutableStateOf(true) }

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.neutral50
				),
				title = {
					Text(text = communityDeck.title)
				},
				navigationIcon = {
					IconButton(onClick = { navigator.popBackStack() }) {
						Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
					}
				}
			)
		},
		bottomBar = {
			BottomNav(navController = navController)
		},
		containerColor = AppTheme.neutral50,
		content = {
			Column(
				modifier = Modifier
					.padding(it)
					.padding(top = 20.dp, start = 20.dp, end = 20.dp)
					.fillMaxSize()
			) {
				AlertWindow(
					title = stringResource(R.string.import_deck_alert_title),
					subText = stringResource(R.string.import_deck_alert_subtext),
					confirmText = stringResource(R.string.import_deck_alert_confirmation),
					confirmTextColor = AppTheme.primary500,
					openDialog = isDialogOpen,
					onCloseDialog = {
						isDialogOpen = false
					},
					onPressConfirm = {
						viewModel.importCommunityDeck(communityDeck)
						isDialogOpen = false
					}
				)

				Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
					CommunityDeckDetailStat(
						title = stringResource(id = R.string.community_deck_downloads_title),
						count = communityDeck.downloads,
						icon = painterResource(id = R.drawable.outline_file_download_24),
						modifier = Modifier.weight(0.5f)
					)
					CommunityDeckDetailStat(
						title = stringResource(id = R.string.community_deck_cards_title),
						count = communityDeck.cards.size,
						icon = painterResource(id = R.drawable.outline_collections_bookmark_24),
						modifier = Modifier.weight(0.5f)
					)
				}
				Text(
					modifier = Modifier.padding(top = 15.dp, bottom = 10.dp),
					text = stringResource(id = R.string.cards_title),
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold
				)

				if (communityDeck.cards.isNotEmpty()) {
					Card(modifier = Modifier
						.fillMaxWidth(),
						colors = CardDefaults.cardColors(
							containerColor = AppTheme.neutral200,
						),
						shape = RoundedCornerShape(35.dp),
						onClick = {
							// navigator.navigate(DeckDetailSearchScreenDestination(communityDeck.deck.id))
						}) {
						Row(
							modifier = Modifier
								.padding(15.dp)
								.fillMaxWidth(),
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.SpaceBetween
						) {
							Text(
								text = stringResource(R.string.search_bar_card_hint),
								color = AppTheme.neutral500
							)
							Icon(imageVector = Icons.Default.Search, contentDescription = "search")
						}
					}
				}
				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(2.dp),
				) {
					item {
						Spacer(modifier = Modifier.height(10.dp))
					}

					if (communityDeck.cards.isEmpty()) {
						item {
							ImageMessage(
								painter = painterResource(id = R.drawable.no_decks_found),
								text = stringResource(id = R.string.no_cards_found)
							)
						}
					} else {
						itemsIndexed(items = communityDeck.cards, itemContent = { index, card ->

							AnimatedVisibility(
								visibleState = state,
								enter = slideInVertically(
									initialOffsetY = { it + 20 },
									animationSpec = tween(
										durationMillis = (index * 100)
									)
								),
								exit = fadeOut()
							) {
								Card(
									onClick = {
										// navigator.navigate(
										//     EditCardScreenDestination(
										//         clickedCardId = card.id,
										//         deckId = communityDeck.id
										//     )
										// )
									},
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
			}
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { isDialogOpen = true },
				contentColor = AppTheme.primary900,
				containerColor = AppTheme.primary300
			) {
				Icon(
					painter = painterResource(id = R.drawable.outline_file_download_24),
					contentDescription = "Import deck"
				)
			}
		})
}

private fun informUser(context: Context, text: String) {
	Toast.makeText(
		context,
		text,
		Toast.LENGTH_SHORT
	).show()
}