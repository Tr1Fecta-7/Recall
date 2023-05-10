package nl.recall.components.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import nl.recall.R
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.studyDeck.model.CardFace
import nl.recall.theme.AppTheme

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun StudyCard(deckWithCards: DeckWithCards) {
    val progressStep = (1.0000f / deckWithCards.cards.size)
    val state = rememberSwipeableCardState()
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
            cards.forEach { (card, cardState) ->
                var cardFace by remember {
                    mutableStateOf(CardFace.Front)
                }

                if (cardState.swipedDirection == null) {
                    FlipCard(
                        cardFace = cardFace,
                        onClick = { cardFace = cardFace.next },
                        modifierFront = Modifier,
                        modifierBack = Modifier
                            .swipableCard(
                                state = cardState,
                                onSwiped = { direction ->
                                    println("The card was swiped to $direction")
                                    progress += progressStep
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