package nl.recall.components.card

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import nl.recall.R
import nl.recall.domain.models.CardPreviewData
import nl.recall.theme.AppTheme

@Composable
fun CardPreview(card: CardPreviewData, paddingValues: PaddingValues, onClick: () -> Unit) {
    var frontTextFieldValue by remember { mutableStateOf(TextFieldValue(card.front)) }
    var backTextFieldValue by remember { mutableStateOf(TextFieldValue(card.back)) }
    val buttonEnabled = remember {
        derivedStateOf { frontTextFieldValue.text.isNotBlank() && backTextFieldValue.text.isNotBlank() }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = AppTheme.white),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 20.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 100.dp, bottom = 0.dp, start = 50.dp, end = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(35.dp),
        ) {
            Column(modifier = Modifier.heightIn(0.dp, 200.dp)) {
                OutlinedTextField(
                    value = frontTextFieldValue,
                    onValueChange = {
                        frontTextFieldValue = it
                        card.front = it.text
                    },
                    label = { Text(text = stringResource(id = R.string.create_card_front_text_field)) },
                    placeholder = { Text(text = stringResource(id = R.string.create_card_front_text_field_placeholder)) },
                )
            }

            Column(modifier = Modifier.heightIn(0.dp, 200.dp).widthIn()) {
                OutlinedTextField(
                    value = backTextFieldValue,
                    onValueChange = {
                        backTextFieldValue = it
                        card.back = it.text
                    },
                    label = { Text(text = stringResource(id = R.string.create_card_back_text_field)) },
                    placeholder = { Text(text = stringResource(id = R.string.create_card_back_text_field_placeholder)) },
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
                onClick = { onClick() },
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .width(300.dp),
                enabled = buttonEnabled.value,
            ) {
                Text(
                    text = card.buttonText,
                    color = Color.White
                )
            }
        }
    }
}