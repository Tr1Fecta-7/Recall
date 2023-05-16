package nl.recall.studyDeck

import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
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
import nl.recall.components.ImageMessage
import nl.recall.components.card.FlipCard
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.studyDeck.StudyDeckViewModel
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.studyDeck.model.SwipeDirection
import nl.recall.presentation.uiState.UIState
import nl.recall.studyDeck.model.BackgroundColors
import nl.recall.studyDeck.model.CardFaceUIState
import nl.recall.studyDeckFinished.StudyDeckFinishedScreen
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
    viewModel.observeDeckWithCards()
    val uiState by viewModel.state.collectAsState()
    val deckWithCards by viewModel.deckWithCards.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val iterator by viewModel.iterator.collectAsState()

    ContentScaffold(title = deckWithCards?.deck?.title,
        navigator = navigator,
        content = { paddingValues ->
            when (uiState) {
                UIState.NORMAL -> {
                    deckWithCards?.let { deckWithCards ->
                        Content(
                            deckWithCards = deckWithCards,
                            paddingValues = paddingValues,
                            progress = progress,
                            iterator = iterator,
                            viewModel = viewModel,
                            navigator = navigator
                        )
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
    navigator: DestinationsNavigator, content: @Composable ((PaddingValues) -> Unit), title: String?
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppTheme.neutral50,
            ),
            title = {
                Text(
                    text = title ?: stringResource(id = R.string.deck_detail_title_placeholder)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                }
            },

            )
    }, content = { paddingValues ->
        content(paddingValues)
    })
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun Content(
    paddingValues: PaddingValues,
    deckWithCards: DeckWithCards,
    progress: Float,
    iterator: Int,
    viewModel: StudyDeckViewModel,
    navigator: DestinationsNavigator
) {
    val animatedProgress = animateFloatAsState(
        targetValue = progress, animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    val cards = deckWithCards.cards.reversed().map { it to rememberSwipeableCardState() }
    var currentColor by remember { mutableStateOf(BackgroundColors.NORMAL) }
    val color = remember { Animatable(BackgroundColors.NORMAL.color) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentColor) {
        color.animateTo(
            targetValue = currentColor.color, animationSpec = tween(500)
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
                            id = R.string.study_progression, iterator, deckWithCards.cards.size
                        )
                    )
                }
            }

        }
        Box(
            Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {

            StudyDeckFinishedScreen(navigator = navigator)


            cards.forEachIndexed() { index, (card, cardState) ->
                var cardFaceUIState by remember {
                    mutableStateOf(CardFaceUIState.Front)
                }

                if (cardState.swipedDirection == null) {
                    LaunchedEffect(cardState.offset.value.x) {
                        if (cardState.offset.value.x < 0.0f) {
                            currentColor = BackgroundColors.CORRECT
                        }

                        if (cardState.offset.value.x > 0.0f) {
                            currentColor = BackgroundColors.WRONG
                        }

                        if (cardState.offset.value.x == 0.0f) {
                            currentColor = BackgroundColors.NORMAL
                        }

                        if (cardState.swipedDirection == Direction.Left || cardState.swipedDirection == Direction.Right) {
                            currentColor = BackgroundColors.NORMAL
                        }
                    }
                    FlipCard(
                        elevation = if (index == iterator) 3 else 0,
                        cardFaceUIState = cardFaceUIState,
                        onClick = { cardFaceUIState = CardFaceUIState.Back },
                        modifierFront = Modifier,
                        modifierBack = Modifier.swipableCard(state = cardState,
                            onSwiped = { direction ->
                                if (direction == Direction.Left) {
                                    viewModel.onSwipeCard(SwipeDirection.LEFT, card)
                                } else {
                                    viewModel.onSwipeCard(SwipeDirection.RIGHT, card)
                                }
                            },
                            onSwipeCancel = {
                                currentColor = BackgroundColors.NORMAL
                            }),
                        front = {
                            Column(
                                modifier = Modifier
                                    .padding(30.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = card.front,
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
                                Column(

                                ) {
                                    Text(
                                        text = card.front,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 25.sp
                                    )
                                    Text(
                                        text = card.back,
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
                                                scope.launch(Dispatchers.Main) {
                                                    cardState.swipe(Direction.Right)
                                                    viewModel.onSwipeCard(
                                                        SwipeDirection.RIGHT, card
                                                    )
                                                    currentColor = BackgroundColors.WRONG
                                                    delay(800)
                                                    currentColor = BackgroundColors.NORMAL
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
                                                    cardState.swipe(Direction.Left)
                                                    viewModel.onSwipeCard(
                                                        SwipeDirection.LEFT, card
                                                    )
                                                    currentColor = BackgroundColors.CORRECT
                                                    delay(800)
                                                    currentColor = BackgroundColors.NORMAL
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
                        },
                    )
                }
            }
        }
    }

}