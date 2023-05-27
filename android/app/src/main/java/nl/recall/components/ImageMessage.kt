package nl.recall.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ImageMessage(
	painter: Painter,
	text: String,
) {
	Column(
		Modifier.fillMaxSize()
	) {
		Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
			verticalArrangement = Arrangement.spacedBy(20.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Image(
				modifier = Modifier.fillMaxWidth(0.8f),
				painter = painter,
				contentDescription = text
			)
			Text(
				text = text,
				textAlign = TextAlign.Center
			)
		}
	}
}
