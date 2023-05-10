package nl.recall.components.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import nl.recall.presentation.studyDeck.model.CardFace
import nl.recall.theme.AppTheme

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
        modifier = Modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.white),
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
                colors = CardDefaults.cardColors(containerColor = AppTheme.white),
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