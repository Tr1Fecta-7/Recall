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
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.sp
import nl.recall.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeckPreview(title: String, cardCount: Int, backgroundColor: Color, icon: String) {
    // TODO: Get colors from theme
    // Colors
    val neutral300 = Color(0xFFCBD5E1)
    val neutral400 = Color(0xFF94A3B8)
    val neutral800 = Color(0xFF1E293B)

    Card(
        onClick = { /* */ },
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, neutral300),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
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
                        .background(backgroundColor)
                ) {
                    Text(icon)
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(title, color = neutral800, fontWeight = FontWeight.Bold)
                    Text(stringResource(id = R.string.deck_card_count).format(cardCount), color = neutral400, fontSize = 14.sp)
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                contentDescription = "arrow right",
                tint = neutral800
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    val title = "Mandarin HSK 1"
    val cardCount = 512
    val backGroundColor = Color(0xFFFFEAEA)
    val icon = "🇨🇳"

    MaterialTheme {
        DeckPreview(title, cardCount, backGroundColor, icon)
    }
}