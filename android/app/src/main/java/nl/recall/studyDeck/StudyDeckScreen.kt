package nl.recall.studyDeck

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.ImageMessage
import nl.recall.components.card.FlipCard
import nl.recall.domain.deck.model.Card
import nl.recall.domain.deck.model.Deck
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.errorScreen.ErrorScreen
import nl.recall.presentation.deckDetail.DeckDetailSearchScreenViewModel
import nl.recall.presentation.deckDetail.model.DeckDetailSearchScreenViewModelArgs
import nl.recall.presentation.studyDeck.StudyDeckViewModel
import nl.recall.presentation.studyDeck.model.StudyDeckViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.studyDeck.model.CardFaceUIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

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
    val deckWithCards by viewModel.deckWithCards.collectAsState()
    ContentScaffold(
        title = deckWithCards?.deck?.title,
        navigator = navigator,
        content = { paddingValues ->
            when (uiState) {
                UIState.NORMAL -> {
                    deckWithCards?.let { deckWithCards ->
                        Content(deckWithCards = deckWithCards, paddingValues = paddingValues)
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
    Scaffold(
        topBar = {
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
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun Content(paddingValues: PaddingValues, deckWithCards: DeckWithCards) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(AppTheme.neutral50)
    ) {
        val progressStep = (1.0000f / deckWithCards.cards.size)
        val state = rememberSwipeableCardState()
        var iterator by remember {
            mutableStateOf(0)
        }
        var progress by remember { mutableStateOf(0.0000f) }
        val animatedProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        ).value
        val cards = deckWithCards.cards.reversed().map { it to rememberSwipeableCardState() }

        Column(

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                LinearProgressIndicator(progress = animatedProgress, Modifier.fillMaxWidth())
            }
            Box(
                Modifier
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                cards.forEachIndexed() { index, (card, cardState) ->
                    var cardFaceUIState by remember {
                        mutableStateOf(CardFaceUIState.Front)
                    }

                    if (cardState.swipedDirection == null) {
                        var elevation by remember {
                            mutableStateOf(0)
                        }
                        if (index == iterator) {
                            elevation = 3
                        }

                        FlipCard(
                            elevation = elevation,
                            cardFaceUIState = cardFaceUIState,
                            onClick = { cardFaceUIState = CardFaceUIState.Back },
                            modifierFront = Modifier,
                            modifierBack = Modifier
                                .swipableCard(
                                    state = cardState,
                                    onSwiped = { direction ->
                                        println("The card was swiped to $direction")
                                        progress += progressStep
                                        iterator++
                                    },
                                    onSwipeCancel = {
                                        println("The swiping was cancelled")
                                    }
                                ),
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
                                            fontSize = 10.sp
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

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.click_to_rotate),
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            },
                        )

                    }
                }
            }
        }
    }
}