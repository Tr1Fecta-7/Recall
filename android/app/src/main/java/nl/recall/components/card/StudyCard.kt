package nl.recall.components.card

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import nl.recall.R
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.theme.AppTheme

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun StudyCard(deckWithCards: DeckWithCards) {
    var cardFace by remember {
        mutableStateOf(CardFace.Front)
    }
    val state = rememberSwipeableCardState()
    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            LinearProgressIndicator(progress = animatedProgress, Modifier.fillMaxWidth())
//            Spacer(Modifier.height(30.dp))
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

        FlipCard(
            cardFace = cardFace,
            onClick = { cardFace = cardFace.next },
            modifierFront = Modifier,
            modifierBack = Modifier
                .swipableCard(
                    state = state,
                    onSwiped = { direction ->
                        println("The card was swiped to $direction")
                    },
                    onSwipeCancel = {
                        println("The swiping was cancelled")
                    }
                ),
            front = {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.white),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Front",
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
                }
            },
            back = {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.white),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(

                        ) {
                            Text(
                                text = "Back",
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                            Text(
                                text = "Answer",
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
                }
            },
        )
    }
}


enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}


@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifierFront: Modifier = Modifier,
    modifierBack: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )

    Box(
        Modifier.fillMaxSize().background(AppTheme.neutral50)

    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = modifierFront
                .fillMaxSize()
                .padding(top = 45.dp, bottom = 18.dp, start = 25.dp, end = 25.dp),
        ) {

        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                },
        ) {
            if (rotation.value <= 90f) {
                Card(
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(20.dp),
                    modifier = modifierFront
                        .fillMaxSize()
                        .padding(top = 70.dp, bottom = 15.dp, start = 15.dp, end = 15.dp),
                    onClick = { onClick(cardFace) },
                ) {
                    front()
                }
            } else {
                Card(
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(20.dp),
                    modifier = modifierBack
                        .fillMaxSize()
                        .padding(top = 70.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
                        .graphicsLayer {
                            rotationY = 180f
                        },
                ) {
                    back()
                }
            }
        }
    }
}