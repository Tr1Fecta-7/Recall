package nl.recall.decksoverview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.ramcosta.composedestinations.annotation.Destination
import nl.recall.components.deck.DeckPreview
import nl.recall.domain.models.DeckPreviewData
import nl.recall.theme.AppTheme

@Destination
@Composable
fun DecksOverviewScreen() {
    val decks = listOf(
        DeckPreviewData(
            title = "Mandarin HSK 1",
            cardCount = 512,
            backgroundColor = "#FFEAEA", //Color("#FFEAEA".toColorInt()),
            emoji = "🇨🇳",
        ),
        DeckPreviewData(
            title = "Maths L1 & L2",
            cardCount = 256,
            backgroundColor = "#FFEAB6", //Color("#FFEAB6".toColorInt()),
            emoji = "🧮",
        )
    )

    Content(decks)
}

@Composable
private fun Content(decks: List<DeckPreviewData>) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                contentColor = AppTheme.primary900,
                containerColor = AppTheme.primary300
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(
                top = paddingValues.calculateTopPadding() + 56.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 14.dp,
                end = 14.dp
            )
        ) {
            Text(
                text = "Decks",
                color = AppTheme.neutral800,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Let's learn something today!",
                color = AppTheme.neutral500
            )

            LazyColumn(
                modifier = Modifier.padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(decks) { deck ->
                    DeckPreview(deck)
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        DecksOverviewScreen()
    }
}