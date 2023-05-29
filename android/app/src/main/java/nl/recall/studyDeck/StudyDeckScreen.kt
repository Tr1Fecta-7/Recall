package nl.recall.studyDeck

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.SwipeableCardState
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.recall.R
import nl.recall.components.AlertWindow
import nl.recall.components.ImageMessage
import nl.recall.components.card.FlipCard
import nl.recall.destinations.StudyDeckFinishedScreenDestination
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.studyDeck.StudyDeckViewModel
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.studyDeck.model.SwipeDirection
import nl.recall.presentation.uiState.UIState
import nl.recall.studyDeck.model.BackgroundColors
import nl.recall.studyDeck.model.CardFaceUIState
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
	}),
) {
	val uiState by viewModel.state.collectAsState()
	val deckWithCards by viewModel.deckWithCards.collectAsState()
	val currentCard by viewModel.currentCard.collectAsState()
	val nextCard by viewModel.nextCard.collectAsState()
	val progress by viewModel.progress.collectAsState()
	val iterator by viewModel.iterator.collectAsState()
	val deckSize by viewModel.deckSize.collectAsState()
	val nextCardAvailability by viewModel.nextCardAvailability.collectAsState()

	ContentScaffold(
		title = deckWithCards?.deck?.title
			?: stringResource(id = R.string.deck_detail_title_placeholder),
		navigator = navigator,
		resetAlgorithm = {
			viewModel.resetDeck()
			navigator.popBackStack()
		},
		content = { paddingValues ->
			when (uiState) {
				UIState.NORMAL -> {
					deckWithCards?.let { deck ->
						currentCard?.let { card ->
							Content(
								paddingValues = paddingValues,
								currentCard = card,
								nextCard = nextCard,
								progress = progress,
								iterator = iterator,
								viewModel = viewModel,
								navigator = navigator,
								deckSize = deckSize,
								nextCardAvailability = nextCardAvailability,
								deckWithCards = deck
							)
						}
					}
				}

				UIState.ERROR -> {
					Column(
						modifier = Modifier
							.padding(paddingValues)
							.background(AppTheme.neutral50)
							.padding(horizontal = 20.dp)
							.fillMaxSize(),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Image(
							painter = painterResource(id = R.drawable.error_image),
							contentDescription = "error"
						)
						Text(text = stringResource(id = R.string.error_when_retrieving_for_cards))
					}
				}

				UIState.LOADING -> {
					Column(
						Modifier
							.fillMaxSize()
							.background(AppTheme.neutral50),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						CircularProgressIndicator()
					}
				}

				UIState.EMPTY -> {
					Column(
						Modifier
							.fillMaxSize()
							.background(AppTheme.neutral50),
						verticalArrangement = Arrangement.SpaceAround
					) {
						ImageMessage(
							painter = painterResource(id = R.drawable.no_decks_found),
							text = stringResource(id = R.string.no_cards_found)
						)
					}
				}
			}
		})


}


@Composable
private fun ContentScaffold(
	navigator: DestinationsNavigator,
	content: @Composable ((PaddingValues) -> Unit),
	title: String,
	resetAlgorithm: () -> (Unit),
) {
	var expandedMoreVert by remember {
		mutableStateOf(false)
	}
	var alertWindowReset by remember {
		mutableStateOf(false)
	}

	var alertWindowInfo by remember {
		mutableStateOf(false)
	}
	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.neutral50,
				),
				title = {
					Text(
						text = title
					)
				},
				navigationIcon = {
					IconButton(onClick = { navigator.popBackStack() }) {
						Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
					}
				},
				actions = {
					IconButton(onClick = {
						expandedMoreVert = !expandedMoreVert
					}) {
						Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
					}
					DropdownMenu(
						expanded = expandedMoreVert,
						onDismissRequest = { expandedMoreVert = false },
						modifier = Modifier
							.background(AppTheme.white)
							.width(180.dp),
					) {
						DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_reset_algorithm)) },
							onClick = {
								alertWindowReset = true
							})
						DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_info_algorithm)) },
							onClick = {
								alertWindowInfo = true
							})
					}
				}
			)
		}, content = { paddingValues ->
			content(paddingValues)
		}
	)
	AlertWindow(
		title = stringResource(id = R.string.dialog_reset_algorithm_title),
		subText = stringResource(id = R.string.dialog_reset_algorithm_text),
		confirmText = stringResource(id = R.string.reset_text),
		confirmTextColor = AppTheme.red700,
		openDialog = alertWindowReset,
		onCloseDialog = {
			expandedMoreVert = false
			alertWindowReset = false
		},
		onPressConfirm = {
			resetAlgorithm()
		}
	)
	AlertWindow(
		title = stringResource(id = R.string.dialog_info_algorithm_title),
		subText = stringResource(id = R.string.dialog_info_algorithm_text),
		confirmText = stringResource(id = R.string.close_text),
		confirmTextColor = AppTheme.neutral800,
		openDialog = alertWindowInfo,
		onCloseDialog = {
			expandedMoreVert = false
			alertWindowInfo = false
		},
		onPressConfirm = {

		},
		cancelButtonVisibility = false
	)
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
private fun Content(
	paddingValues: PaddingValues,
	currentCard: Card,
	nextCard: Card?,
	progress: Float,
	iterator: Int,
	viewModel: StudyDeckViewModel,
	navigator: DestinationsNavigator,
	deckSize: Int,
	nextCardAvailability: Boolean,
	deckWithCards: DeckWithCards,
) {
	var cardFaceUIState by remember {
		mutableStateOf(CardFaceUIState.Front)
	}
	val animatedProgress = animateFloatAsState(
		targetValue = progress, animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
	).value
	var currentBackgroundColor by remember { mutableStateOf(BackgroundColors.NORMAL) }
	val color = remember { Animatable(BackgroundColors.NORMAL.color) }
	val scope = rememberCoroutineScope()
	val cardStates = ArrayList<SwipeableCardState>()

	for (i in 0..deckSize) {
		cardStates.add(rememberSwipeableCardState())
	}

	if (cardStates.size <= deckSize) {
		cardStates.add(rememberSwipeableCardState())
	}


	LaunchedEffect(cardStates[iterator].offset.value.x) {
		when {
			(cardStates[iterator].offset.value.x < 0.0f) -> {
				currentBackgroundColor = BackgroundColors.CORRECT
			}

			(cardStates[iterator].offset.value.x > 0.0f) -> {
				currentBackgroundColor = BackgroundColors.WRONG
			}

			(cardStates[iterator].offset.value.x == 0.0f || cardStates[iterator].swipedDirection != null) -> {
				currentBackgroundColor = BackgroundColors.NORMAL
			}
		}
	}

	LaunchedEffect(currentBackgroundColor) {
		color.animateTo(
			targetValue = currentBackgroundColor.color, animationSpec = tween(500)
		)
	}

	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxSize()
			.background(color.value)
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			LinearProgressIndicator(progress = animatedProgress, Modifier.fillMaxWidth())
			Row(
				Modifier
					.fillMaxWidth()
					.padding(13.dp), horizontalArrangement = Arrangement.End
			) {
				Card(
					shape = RoundedCornerShape(10.dp)
				) {
					Text(
						fontSize = 14.sp, modifier = Modifier.padding(4.dp), text = stringResource(
							id = R.string.study_progression, iterator, deckSize
						)
					)
				}
			}

		}
		Box(
			Modifier
				.padding(24.dp)
				.fillMaxSize(),
		) {
			LaunchedEffect(key1 = progress) {
				if (progress == 1.0f) {
					navigator.popBackStack()
					navigator.navigate(
						StudyDeckFinishedScreenDestination(
							title = deckWithCards.deck.title,
							deckWithCards.cards.size
						)
					)
				}
			}
			if (nextCard != null) {
				Card(
					colors = CardDefaults.cardColors(containerColor = AppTheme.white),
					elevation = CardDefaults.cardElevation(3.dp),
					shape = RoundedCornerShape(20.dp),
					modifier = Modifier
						.fillMaxSize()
						.padding(bottom = 45.dp, start = 10.dp, end = 10.dp)
				) {
					Column(
						modifier = Modifier
							.padding(30.dp)
							.fillMaxSize(),
						verticalArrangement = Arrangement.SpaceBetween
					) {
						Text(
							text = nextCard.front, fontWeight = FontWeight.Bold, fontSize = 25.sp
						)
						Row(
							Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
						) {
							Text(
								text = stringResource(id = R.string.click_to_rotate),
								fontSize = 10.sp,
								lineHeight = 12.sp,
							)
						}
					}
				}
			}

			if (nextCardAvailability) {

				FlipCard(cardFaceUIState = cardFaceUIState,
					modifierBack = Modifier.swipableCard(
						cardStates[iterator], onSwiped = { direction ->
							scope.launch {
								cardFaceUIState = CardFaceUIState.Front
//								delay(100)
								if (direction == Direction.Left) {
									viewModel.onSwipeCard(
										SwipeDirection.LEFT, currentCard
									)
								} else {
									viewModel.onSwipeCard(
										SwipeDirection.RIGHT, currentCard
									)
								}
								delay(200)
								viewModel.getNextCard()
							}
						}, blockedDirections = listOf(Direction.Down, Direction.Up)
					),
					onClick = { cardFaceUIState = CardFaceUIState.Back },
					elevation = if (deckSize - 1 == iterator) {
						3
					} else {
						0
					},
					front = {
						Column(
							modifier = Modifier
								.padding(30.dp)
								.fillMaxSize(),
							verticalArrangement = Arrangement.SpaceBetween
						) {
							Text(
								text = currentCard.front,
								fontWeight = FontWeight.Bold,
								fontSize = 25.sp
							)
							Row(
								Modifier.fillMaxWidth(),
								horizontalArrangement = Arrangement.Center
							) {
								Text(
									text = stringResource(id = R.string.click_to_rotate),
									fontSize = 10.sp,
									lineHeight = 12.sp,
								)
							}
						}
					},
					back = {
						Column(
							modifier = Modifier
								.padding(30.dp)
								.fillMaxSize(),
							verticalArrangement = Arrangement.SpaceBetween
						) {
							Column {
								Text(
									text = currentCard.front,
									fontWeight = FontWeight.Bold,
									fontSize = 25.sp
								)
								Text(
									text = currentCard.back,
									fontWeight = FontWeight.Light,
									fontSize = 25.sp
								)
							}
							Column(
								Modifier.fillMaxWidth(),
								verticalArrangement = Arrangement.spacedBy(10.dp)
							) {
								Text(
									fontSize = 10.sp,
									lineHeight = 12.sp,
									text = stringResource(id = R.string.instructions_back_card_study_deck)
								)

								Row(
									Modifier.fillMaxWidth(),
									horizontalArrangement = Arrangement.SpaceAround
								) {
									Button(modifier = Modifier.size(50.dp),
										shape = CircleShape,
										contentPadding = PaddingValues(0.dp),
										colors = ButtonDefaults.buttonColors(
											containerColor = AppTheme.red300
										),
										onClick = {
											scope.launch {
												cardStates[iterator].swipe(Direction.Right)
												cardFaceUIState = CardFaceUIState.Front
												viewModel.onSwipeCard(
													SwipeDirection.RIGHT, currentCard
												)
												delay(100)
												viewModel.getNextCard()
												currentBackgroundColor = BackgroundColors.WRONG
												delay(500)
												currentBackgroundColor = BackgroundColors.NORMAL
											}
										}) {
										Icon(
											tint = AppTheme.red700,
											imageVector = Icons.Default.Close,
											contentDescription = "wrong"
										)
									}
									Button(

										modifier = Modifier.size(50.dp),
										shape = CircleShape,
										contentPadding = PaddingValues(0.dp),
										colors = ButtonDefaults.buttonColors(
											containerColor = AppTheme.primary300
										),
										onClick = {
											scope.launch(Dispatchers.Unconfined) {
												cardStates[iterator].swipe(Direction.Left)
												cardFaceUIState = CardFaceUIState.Front
												viewModel.onSwipeCard(
													SwipeDirection.LEFT, currentCard
												)
												delay(100)
												viewModel.getNextCard()
												currentBackgroundColor =
													BackgroundColors.CORRECT
												delay(500)
												currentBackgroundColor = BackgroundColors.NORMAL
											}
										}) {
										Icon(
											tint = AppTheme.primary700,
											imageVector = Icons.Default.Check,
											contentDescription = "correct"
										)
									}
								}
							}
						}
					}
				)

			}
		}
	}
}
