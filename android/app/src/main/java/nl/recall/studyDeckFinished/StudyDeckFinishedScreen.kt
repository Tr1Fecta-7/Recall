package nl.recall.studyDeckFinished

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import nl.recall.R
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StudyDeckFinishedScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val oldStreak = context.applicationContext.getSharedPreferences(
        stringResource(id = R.string.shared_preferences_streak_file),
        0
    ).getInt(
        stringResource(id = R.string.shared_preferences_current_streak), 0
    );
    var currentStreak by remember { mutableStateOf(oldStreak) }
    LaunchedEffect(key1 = true) {
        delay(1000)
        currentStreak = checkStreak(context = context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val widthSize = 150.dp
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .width(widthSize)
                    .height(widthSize),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(widthSize / 2))
                        .background(AppTheme.primary100)
                        .width(widthSize)
                        .height(widthSize)
                        .border(
                            width = 1.dp,
                            color = AppTheme.primary300,
                            shape = RoundedCornerShape(widthSize / 2)
                        )
                        .padding(bottom = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "\uD83C\uDFC6", fontSize = 90.sp)
                }
            }
            Column(
                modifier = Modifier.padding(top = 10.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.completed),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = stringResource(id = R.string.good_job),
                    fontSize = 16.sp,
                    color = AppTheme.neutral400,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(75.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(AppTheme.primary100)
                    .border(
                        width = 1.dp,
                        color = AppTheme.primary300,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row() {
                    Text(text = stringResource(id = R.string.fire_emoji))
                    AnimatedContent(
                        targetState = currentStreak,
                        transitionSpec = {
                            slideInVertically { -it } with slideOutVertically { it }
                        }) {
                        Text(text = it.toString())
                    }
                }
                Text(text = stringResource(id = R.string.day_streak))
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Button(
                onClick = { navigator.popBackStack() },
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = AppTheme.white
                )
                Text(
                    text = stringResource(id = R.string.back_to_overview),
                    color = AppTheme.white,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}


private fun checkStreak(context: Context): Int {
    val sharedPreferences =
        context.applicationContext.getSharedPreferences("streakFile", Context.MODE_PRIVATE)
    val edit = sharedPreferences.edit()
    val calendar = Calendar.getInstance()
    val currentDay: Int = calendar.get(DAY_OF_YEAR)
    val lastStreakDay = sharedPreferences.getInt("lastStreakDay", 0)
    val currentStreakDay = sharedPreferences.getInt("currentStreak", 0)
    if (currentStreakDay == currentDay) return currentStreakDay

    val streak = if (lastStreakDay == currentDay - 1) {
        edit.putInt("currentStreak", currentStreakDay + 1)
        edit.putInt("lastStreakDay", currentDay)
        currentStreakDay + 1
    } else {
        edit.putInt("lastStreakDay", currentDay)
        edit.putInt("currentStreak", 1)
        1
    }
    edit.apply()
    return streak
//    val edit = sharedPreferences.edit()
//    edit.putString(stringResource(id = R.string.shared_preferences_start_streak_day))

}