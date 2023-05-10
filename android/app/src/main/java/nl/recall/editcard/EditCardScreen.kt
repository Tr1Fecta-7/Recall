package nl.recall.editcard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.components.card.CardPreview
import nl.recall.createcard.MainContent
import nl.recall.domain.models.CardPreviewData
import nl.recall.theme.AppTheme
import java.util.Date

@Destination
@Composable
fun EditCardScreen(navController: NavController, navigator: DestinationsNavigator,
                   clickedCardId: Long, deckId: Long) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppTheme.neutral50),
                title = { Text(stringResource(id = R.string.edit_card_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        bottomBar = { BottomNav(navController = navController) },
        content = { MainContent(navigator = navigator, it, clickedCardId, deckId) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues,
                clickedCardId: Long, deckId: Long) {
    val card = CardPreviewData(
        front = "",
        back = "",
        buttonText = stringResource(id = R.string.edit_card_title)
    )

    CardPreview(card = card, paddingValues = paddingValues, onClick = {

    })
}
