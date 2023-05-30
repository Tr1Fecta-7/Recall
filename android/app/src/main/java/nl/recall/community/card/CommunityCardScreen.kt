package nl.recall.community.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.ImageMessage
import nl.recall.presentation.community.card.CommunityCardViewModel
import nl.recall.presentation.community.card.model.CommunityCardViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun CommunityCardScreen(
	cardId: Long,
	title: String,
	navigator: DestinationsNavigator,
	viewModel: CommunityCardViewModel = koinViewModel(parameters = {
		parametersOf(CommunityCardViewModelArgs(cardId))
	}),
) {
	viewModel.getCardById()
	val uiState by viewModel.state.collectAsState()
	val communityCard by viewModel.communityCard.collectAsState()

	Content(title = title, navigator = navigator) {

		when (uiState) {
			UIState.NORMAL -> {
				communityCard?.let { card ->
					Column {
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
				}
			}

			UIState.LOADING -> {
				Column(
					Modifier.fillMaxWidth(),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					CircularProgressIndicator()
				}
			}

			UIState.EMPTY -> {
				Column(
					Modifier.fillMaxSize()
				) {
					ImageMessage(
						painter = painterResource(id = R.drawable.no_decks_found),
						text = stringResource(id = R.string.no_card_found)
					)
				}
			}

			UIState.ERROR -> {
				Column(
					Modifier.fillMaxSize()
				) {
					ImageMessage(
						painter = painterResource(id = R.drawable.error_image),
						text = stringResource(id = R.string.error_when_retrieving_card)
					)
				}
			}
		}
	}
}

@Composable
private fun Content(
	title: String,
	navigator: DestinationsNavigator,
	content: @Composable () -> Unit,
) {
	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = AppTheme.neutral50,
				),
				title = { Text(text = title) },
				navigationIcon = {
					IconButton(onClick = { navigator.popBackStack() }) {
						Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
					}
				}
			)
		},
		containerColor = AppTheme.neutral50
	) { paddingValues ->
		Column(
			modifier = Modifier
				.padding(paddingValues)
				.padding(20.dp)
		) {
			Card(
				colors = CardDefaults.cardColors(containerColor = AppTheme.white),
				elevation = CardDefaults.cardElevation(3.dp),
				shape = RoundedCornerShape(20.dp),
				modifier = Modifier
					.fillMaxSize()
					.padding(bottom = 15.dp, start = 10.dp, end = 10.dp)
			) {
				Column(
					modifier = Modifier
						.padding(30.dp)
						.fillMaxSize(),
					verticalArrangement = Arrangement.SpaceBetween
				) {
					content()
				}
			}
		}
	}
}