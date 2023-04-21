package nl.recall.components.deck

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.recall.R
import nl.recall.decksoverview.DeckPreviewData
import nl.recall.theme.AndroidAppTheme
import nl.recall.theme.AppTheme

@Composable
fun DeckPreview(deck: DeckPreviewData) {
    Card(
        onClick = { /* */ },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, AppTheme.neutral200),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(AppTheme.white)
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(deck.backgroundColor)
                ) {
                    Text(deck.emoji)
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        deck.title,
                        color = AppTheme.neutral800,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(id = R.string.deck_card_count).format(deck.cardCount),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.neutral400
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = "arrow right",
                tint = AppTheme.neutral800
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    val deck = DeckPreviewData(
        title = "Mandarin HSK 1",
        cardCount = 512,
        backgroundColor = Color(0xFFFFEAEA),
        emoji = "🇨🇳",
    )

    AndroidAppTheme {
        DeckPreview(deck)
    }
}