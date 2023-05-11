package nl.recall.components.card


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import nl.recall.R
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.studyDeck.model.CardFaceUIState

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun StudyCard(deckWithCards: DeckWithCards) {
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
//            OutlinedButton(
//                onClick = {
//                    if (progress < 1f) progress += 0.1f
//                }
//            ) {
//                Text("Increase")
//            }
//
//            OutlinedButton(
//                onClick = {
//                    if (progress > 0f) progress -= 0.1f
//                }
//            ) {
//                Text("Decrease")
//            }
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
//                        && (index == iterator || index ==(iterator-1)) // && (index >= iterator-1)
                if (cardState.swipedDirection == null && index == iterator && (index >= iterator-1)) {
                    FlipCard(
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