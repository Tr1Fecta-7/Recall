package nl.recall.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import nl.recall.NavGraphs
import nl.recall.R
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.destinations.DecksOverviewSearchScreenDestination
import nl.recall.theme.AndroidAppTheme
import nl.recall.theme.AppTheme

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Deck(DecksOverviewScreenDestination, R.drawable.outline_inbox_24, R.string.nav_deck_label),
    Community(
        DecksOverviewSearchScreenDestination /* TODO: Change to Community destination once it's made */,
        R.drawable.outline_collections_bookmark_24,
        R.string.nav_community_label
    ),
    Settings(
        DecksOverviewSearchScreenDestination /* TODO: Change to Settings destination once it's made */,
        R.drawable.outline_settings_24,
        R.string.nav_settings_label
    ),
}

@Composable
fun BottomNav(
    navController: NavController,
) {
    val currentDestination = navController.currentDestinationAsState().value
        ?: NavGraphs.root.startDestination

    BottomAppBar(
        containerColor = AppTheme.neutral50,
        modifier = Modifier.shadow(elevation = 20.dp)
    ) {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = { navController.navigate(destination.direction) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                label = { Text(text = stringResource(id = destination.label)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppTheme.primary100,
                    selectedIconColor = AppTheme.primary800,
                    selectedTextColor = AppTheme.neutral600,
                    unselectedIconColor = AppTheme.neutral600
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AndroidAppTheme {
        BottomNav(rememberNavController())
    }
}