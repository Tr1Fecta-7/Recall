package nl.recall.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.destinations.OnboardingScreenDestination
import nl.recall.onboarding.model.OnboardingItems
import nl.recall.presentation.deck.overview.model.DeckOverviewNavigationAction
import nl.recall.presentation.onboarding.OnboardingViewModel
import nl.recall.presentation.onboarding.model.OnboardingNavigationAction
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@NavGraph
annotation class OnboardingNavGraph(
    val start: Boolean = false
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RootNavGraph
@OnboardingNavGraph(start = true)
@Destination
@Composable
fun OnboardingScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigation.collect {
            when(it) {
                OnboardingNavigationAction.OPEN_DECKOVERVIEW -> navigator.navigate(
                    DecksOverviewScreenDestination
                )
            }
        }
    }

    Scaffold(
        containerColor = AppTheme.neutral50,
        content = { MainContent(navigator = navigator, viewModel) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    navigator: DestinationsNavigator,
    viewModel: OnboardingViewModel
) {
    val pagerState = rememberPagerState()
    val items = OnboardingItems.getData()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(pageCount = items.size, state = pagerState) { page ->
            OnboardingPage(items = items[page])
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .offset(y = -(30).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val isLastPage = pagerState.currentPage == items.size - 1
            var shouldShowButton by remember { mutableStateOf(false) }
            shouldShowButton = isLastPage

            AnimatedVisibility(
                visible = shouldShowButton,
                enter = fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300, easing = LinearEasing))
            ) {
                Button(
                    onClick = {
                        viewModel.setOnboarding(completed = true)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Get Started")
                }
            }

            Text(
                text = stringResource(id = items[pagerState.currentPage].indicatorText),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            CustomPagerIndicator(
                pagerState = pagerState,
                pageCount = items.size,
                activeColor = AppTheme.primary400,
                inactiveColor = AppTheme.neutral300
            )
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
            val isSelected = pageIndex == pagerState.currentPage
            val width by animateDpAsState(
                targetValue = if (isSelected) 50.dp else 10.dp,
                animationSpec = tween(durationMillis = 400, easing = EaseInOutCirc) //spring(dampingRatio = 1.5f)
            )

            val dotShape = if (pageIndex == pagerState.currentPage) {
                RoundedCornerShape(10.dp)
            } else {
                CircleShape
            }

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(width)
                    .background(color = if (isSelected) activeColor else inactiveColor, shape = dotShape)
            )

            Spacer(modifier = Modifier.width(4.dp))
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