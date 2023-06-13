package nl.recall.settings

import android.content.Context
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
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.BottomNav
import nl.recall.domain.deck.model.AlgorithmStrength
import nl.recall.presentation.settings.SettingsViewModel
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SettingsScreen(
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    var selectedStrength by remember { mutableStateOf(getStrength(context)) }
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

        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(id = R.string.algorithm_strength_setting_name))
            DropdownMenuBox(selectedStrength = selectedStrength, changeStrength = changeStrength)
        }
        Text(
            text = stringResource(id = R.string.algorithm_strength_setting_description),
            color = AppTheme.neutral500,
            fontSize = 10.sp,
            lineHeight = 12.sp
        )

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
                            if (changeStrength(saveStrength(context, item))) {
                                Toast.makeText(
                                    context,
                                    "Algorithm strength changed to ${item.strengthName}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun saveStrength(
    context: Context,
    algorithmStrength: AlgorithmStrength
): AlgorithmStrength {
    context.applicationContext.getSharedPreferences("settingsFile", Context.MODE_PRIVATE)?.edit {
        putString("strength", algorithmStrength.toString())
    }
    return algorithmStrength
}

private fun getStrength(
    context: Context,
): AlgorithmStrength {
    val strength =
        context.applicationContext.getSharedPreferences("settingsFile", Context.MODE_PRIVATE)
            .getString("strength", AlgorithmStrength.NORMAL.toString())
    return AlgorithmStrength.valueOf(strength ?: AlgorithmStrength.NORMAL.toString())
}