package nl.recall.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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

@RootNavGraph(start = true)
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
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(pageCount = items.size, state = pagerState) { page ->
            OnboardingPage(items = items[page])
        }

        Column(
            modifier = Modifier.padding(16.dp).offset(y = -(30).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (pagerState.currentPage != items.size - 1) {
                Text(
                    text = stringResource(id = items[pagerState.currentPage].indicatorText),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                CustomPagerIndicator(
                    pagerState = pagerState,
                    pageCount = items.size,
                    activeColor = AppTheme.primary400, // Color for active (current) dot
                    inactiveColor = AppTheme.neutral300 // Color for inactive (passed) dots
                )
            } else {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Get Started")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    activeColor: Color,
    inactiveColor: Color
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { pageIndex ->
            val dotShape = if (pageIndex == pagerState.currentPage) {
                RoundedCornerShape(10.dp)
            } else {
                CircleShape
            }

            val dotColor = if (pageIndex == pagerState.currentPage) {
                activeColor
            } else {
                inactiveColor
            }

            Box(
                modifier = Modifier
                    .size(
                        width = if (pageIndex == pagerState.currentPage) 50.dp else 8.dp,
                        height = 8.dp
                    )
                    .background(color = dotColor, shape = dotShape)
                    .padding(horizontal = 4.dp)
            )

            if (pageIndex < pageCount - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun OnboardingPage(items: OnboardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 125.dp, start = 16.dp, end = 16.dp)
    ) {
        val painter = painterResource(id = items.image)
        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            painter = painter,
            contentDescription = "",
        )

        Text(
            text = stringResource(id = items.title),
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(id = items.description),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp),
            color = AppTheme.neutral400,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            //letterSpacing = 1.sp,

        )
    }
}