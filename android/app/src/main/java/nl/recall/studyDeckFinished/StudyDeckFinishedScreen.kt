package nl.recall.studyDeckFinished
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import nl.recall.R
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Composable
fun StudyDeckFinishedScreen(navigator: DestinationsNavigator){
    val context = LocalContext.current
    val currentStreak: Int = CheckStreak(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier
                .clip(RoundedCornerShape(75.dp))
                .background(AppTheme.primary100)
                .width(150.dp)
                .height(150.dp)
                .border(
                    width = 1.dp,
                    color = AppTheme.primary300,
                    shape = RoundedCornerShape(75.dp)
                )
                .padding(bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "\uD83C\uDFC6", fontSize = 100.sp)
            }
        }
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
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = String.format(stringResource(id = R.string.streak_number), currentStreak))
            Text(text = stringResource(id = R.string.day_streak))
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Button(
                onClick = { navigator.popBackStack() },
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
            ){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back", tint = AppTheme.white)
                Text(text = stringResource(id = R.string.back_to_overview), color = AppTheme.white, modifier = Modifier.padding(start = 10.dp))
            }
        }
    }
}

@Composable
private fun CheckStreak(context: Context): Int{
    val sharedPreferences = context.applicationContext.getSharedPreferences(stringResource(id = R.string.shared_preferences_streak_file), Context.MODE_PRIVATE)
    val edit = sharedPreferences.edit()
    val calendar = Calendar.getInstance()
    val currentDay: Int = calendar.get(DAY_OF_YEAR)
    val lastStreakDay = sharedPreferences.getInt(stringResource(id = R.string.shared_preferences_last_streak_day), 0)
    val currentStreakDay = sharedPreferences.getInt(stringResource(id = R.string.shared_preferences_current_streak), 0)
    if(currentStreakDay == currentDay) return currentStreakDay

    return if (lastStreakDay == currentStreakDay - 1){
        edit.putInt(stringResource( id= R.string.shared_preferences_current_streak), currentStreakDay + 1)
        edit.putInt(stringResource(id = R.string.shared_preferences_last_streak_day), currentDay)
        currentStreakDay+1
    } else {
        edit.putInt(stringResource(id = R.string.shared_preferences_last_streak_day), currentDay)
        edit.putInt(stringResource(id = R.string.shared_preferences_current_streak), 1)
        1
    }
//    val edit = sharedPreferences.edit()
//    edit.putString(stringResource(id = R.string.shared_preferences_start_streak_day))

}