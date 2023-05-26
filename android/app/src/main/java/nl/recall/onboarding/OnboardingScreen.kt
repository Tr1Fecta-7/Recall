package nl.recall.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.onboarding.model.OnboardingItems
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(navController: NavController, navigator: DestinationsNavigator, paddingValues: PaddingValues) {
    val pagerState = rememberPagerState()
    val items = OnboardingItems.getData()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(pageCount = items.size, state = pagerState) { page ->
            OnboardingPage(items = items[page])
        }

        HorizontalPagerIndicator(
            modifier = Modifier.padding(16.dp),
            pagerState = pagerState,
            pageCount = items.size,
            indicatorWidth = 10.dp,
        )
    }


}


@Composable
fun OnboardingPage(items: OnboardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
    ) {
        val painter = painterResource(id = items.image)
        Image(
            modifier = Modifier.size(450.dp),
            painter = painter,
            contentDescription = "",
        )

        val yPadding = (-30).dp
        Text(
            text = stringResource(id = items.title),
            modifier = Modifier.offset(y = yPadding),
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(id = items.desc),
            modifier = Modifier
                .offset(y = yPadding)
                .padding(start = 8.dp, end = 8.dp),
            color = AppTheme.neutral400,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            //letterSpacing = 1.sp,

        )
    }
}