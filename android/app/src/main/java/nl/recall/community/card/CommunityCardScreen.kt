package nl.recall.community.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.theme.AppTheme

@Destination
@Composable
fun CommunityCardScreen(
	front: String,
	back: String,
	title: String,
	navigator: DestinationsNavigator,
) {
	Content(title = title, navigator = navigator) {
		Column {
			Text(
				text = front,
				fontWeight = FontWeight.Bold,
				fontSize = 25.sp
			)
			Text(
				text = back,
				fontWeight = FontWeight.Light,
				fontSize = 25.sp
			)
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