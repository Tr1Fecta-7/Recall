package nl.recall.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.presentation.settings.SettingsViewModel
import nl.recall.presentation.settings.model.AlgorithmStrength
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SettingsScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val strength by viewModel.strength.collectAsState()
    var selectedStrength by remember { mutableStateOf(strength) }
    val changeStrength: (AlgorithmStrength) -> (Boolean) = {
        selectedStrength = it
        viewModel.updateStrength(it)
    }

    Scaffold(
        containerColor = AppTheme.neutral50,
        bottomBar = { BottomNav(navController = navController) }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            selectedStrength = selectedStrength,
            changeStrength = changeStrength
        )
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    selectedStrength: AlgorithmStrength,
    changeStrength: (AlgorithmStrength) -> (Boolean)
) {
    Column(
        Modifier.padding(
            top = paddingValues.calculateTopPadding() + 56.dp,
            bottom = paddingValues.calculateBottomPadding(),
            start = 14.dp,
            end = 14.dp
        )
    ) {
        Text(
            text = stringResource(id = R.string.settings_title),
            color = AppTheme.neutral800,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = stringResource(id = R.string.deck_overview_subtitle),
            color = AppTheme.neutral500
        )

        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(id = R.string.algorithm_strength_setting_name))
            DropdownMenuBox(selectedStrength = selectedStrength, changeStrength = changeStrength)
        }


    }
}

@Composable
private fun DropdownMenuBox(
    selectedStrength: AlgorithmStrength,
    changeStrength: (AlgorithmStrength) -> (Boolean)
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .width(125.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedStrength.strengthName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                modifier = Modifier.background(AppTheme.white),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                AlgorithmStrength.values().forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.strengthName) },
                        onClick = {
                            expanded = false
                            if (changeStrength(item)) {
                                Toast.makeText(context, "Algorithm strength changed to ${item.strengthName}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
}