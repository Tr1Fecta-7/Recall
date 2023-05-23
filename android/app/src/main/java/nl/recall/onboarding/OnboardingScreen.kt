package nl.recall.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.theme.AppTheme

//@RootNavGraph(start = true)
@Destination
@Composable
fun OnboardingScreen(navController: NavController, navigator: DestinationsNavigator) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        content = { MainContent(navController = navController, navigator = navigator, it) }
    )
}

@Composable
fun MainContent(navController: NavController, navigator: DestinationsNavigator, paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val painter = painterResource(id = R.drawable.first_onboarding)
        Image(
            modifier = Modifier
                .aspectRatio(ratio = painter.intrinsicSize.height /
                        painter.intrinsicSize.width)
                .fillMaxWidth(),
            painter = painter,
            contentDescription = "",
        )
        Text("Recall")
    }
}