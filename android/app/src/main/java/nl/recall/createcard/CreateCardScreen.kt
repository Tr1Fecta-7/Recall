package nl.recall.createcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary

@Destination
@Composable
fun CreateCardScreen(navigator: DestinationsNavigator) {
    Scaffold(
        containerColor = AppTheme.neutral50,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppTheme.neutral50),
                title = { Text(stringResource(id = R.string.create_card_title)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        content = { MainContent(navigator = navigator, it) }
    )
}

@Composable
fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues) {
    var frontTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var backTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var createButtonEnabled = remember {
        derivedStateOf { frontTextFieldValue.text.isNotBlank() && backTextFieldValue.text.isNotBlank() }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = AppTheme.white),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 20.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 100.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(35.dp),
        ) {
            Column(modifier = Modifier.heightIn(0.dp, 200.dp)) {
                OutlinedTextField(
                    value = frontTextFieldValue,
                    onValueChange = {
                        frontTextFieldValue = it
                    },
                    label = { Text(text = stringResource(id = R.string.create_card_front_text_field))},
                    placeholder = { Text(text = stringResource(id = R.string.create_card_front_text_field_placeholder))},
                )
            }

            Column(modifier = Modifier.heightIn(0.dp, 200.dp)) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .width(300.dp),
                enabled = createButtonEnabled.value,
            ) {
                Text(
                    text = stringResource(id = R.string.create_card_title),
                    color = Color.White
                )
            }
        }


    }
}