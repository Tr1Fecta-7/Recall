package nl.recall.components.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import nl.recall.studyDeck.model.CardFaceUIState
import nl.recall.theme.AppTheme

@Composable
fun FlipCard(
    cardFaceUIState: CardFaceUIState,
    onClick: (CardFaceUIState) -> Unit,
    modifierFront: Modifier = Modifier,
    modifierBack: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
    elevation: Int
) {
    val rotation = animateFloatAsState(
        targetValue = cardFaceUIState.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )


    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.white),
                elevation = CardDefaults.cardElevation(elevation.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = modifierFront
                    .fillMaxSize()
                    .padding(bottom = 15.dp, start = 10.dp, end = 10.dp),
                onClick = { onClick(cardFaceUIState) },
            ) {
                front()
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.white),
                elevation = CardDefaults.cardElevation(3.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = modifierBack
                    .fillMaxSize()
                    .padding(bottom = 15.dp, start = 10.dp, end = 10.dp)
                    .graphicsLayer {
                        rotationY = 180f
                    },
            ) {
                back()
            }
        }

    }
}