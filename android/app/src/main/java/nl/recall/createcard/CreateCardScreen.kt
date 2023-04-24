package nl.recall.createcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.theme.AppTheme

@Destination
@Composable
fun CreateCardScreen(navigator: DestinationsNavigator) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.create_card_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        content = { MainContent(navigator = navigator, it) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues) {
    var frontTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var backTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Card(
        colors = CardDefaults.cardColors(containerColor = AppTheme.white),
        modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(top = 60.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
        .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = frontTextFieldValue,
                onValueChange = {
                    frontTextFieldValue = it
                },
                label = { Text(text = stringResource(id = R.string.create_card_front_text_field))},
                placeholder = { Text(text = stringResource(id = R.string.create_card_front_text_field_placeholder))},
            )

            OutlinedTextField(
                value = backTextFieldValue,
                onValueChange = {
                    backTextFieldValue = it
                },
                label = { Text(text = stringResource(id = R.string.create_card_back_text_field))},
                placeholder = { Text(text = stringResource(id = R.string.create_card_back_text_field_placeholder))},
            )
        }
    }
}