package nl.recall.components.communityDeck

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.recall.R
import nl.recall.theme.AppTheme

@Composable
fun CommunityDeckDetailStat(
	title: String,
	count: Int,
	icon: Painter,
	modifier: Modifier
) {
	Row(
		modifier = modifier
			.background(AppTheme.white)
			.clip(RoundedCornerShape(10.dp))
			.border(
				width = 1.dp,
				color = AppTheme.neutral200,
				shape = RoundedCornerShape(10.dp)
			)
			.padding(horizontal = 12.dp, vertical = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.size(36.dp)
				.clip(RoundedCornerShape(10.dp))
				.background(AppTheme.primary100)
		) {
			Icon(
				painter = icon,
				contentDescription = "Download count",
				tint = AppTheme.primary600
			)
		}
		Column {
			Text(
				text = "# $count",
				fontWeight = FontWeight.Bold,
				color = AppTheme.neutral600
			)
			Text(
				text = title,
				color = AppTheme.neutral400,
				fontSize = 12.sp
			)
		}
	}
}