package nl.recall.components.deck

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import nl.recall.R
import nl.recall.components.ColorPickerWindow
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary

@Composable
fun DeckFrontEndComponent(
    paddingValues: PaddingValues,
    onSubmitDeck: () -> Unit,
    showAlert: Boolean,
    toggleAlert: () -> Unit,
    preSelectedColor: String,
    onSetColor: (String) -> Unit,
    deckTitleTextField: TextFieldValue,
    onDeckTextFieldValueChange: (TextFieldValue) -> Unit,
    deckColor: String,
    emojiTextfield: TextFieldValue,
    onEmojiTextFieldValueChange: (TextFieldValue) -> Unit,
    validationEmoji: Boolean,
    validationTitle: Boolean,
    submitButtonText: String
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //hidden by default
            ColorPickerWindow(
                confirmText = "Select color",
                confirmTextColor = AppTheme.primary300,
                openDialog = showAlert,
                onCloseDialog = { toggleAlert() },
                onSelectColor = { color -> onSetColor(color) },
                preSelectedColor = preSelectedColor,
            )


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize().weight(1f)
            ) {
                OutlinedTextField(
                    value = deckTitleTextField,
                    label = { Text(text = stringResource(id = R.string.create_deck_create_text_field)) },
                    onValueChange = { newText ->
                        onDeckTextFieldValueChange(newText)
                    },
                    placeholder = { Text(text = stringResource(id = R.string.create_deck_create_text_field_placeholder)) },
                )

                Text(
                    text = stringResource(id = R.string.create_deck_select_icon),
                    modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 30.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .background(
                            Color(
                                deckColor.toColorInt()
                            )
                        ),
                ) {
                    TextField(
                        value = emojiTextfield,
                        onValueChange = { newText ->
                            onEmojiTextFieldValueChange(newText)
                        },
                        modifier = Modifier.background(color = Color.White),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center, fontSize = 120.sp
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(deckColor.toColorInt()),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),

                        )
                }
                Button(
                    onClick = { toggleAlert() }, modifier = Modifier
                        .padding(5.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.create_deck_select_background_color),
                        color = AppTheme.white
                    )
                }
            }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    enabled = validationTitle && validationEmoji,
                    onClick = {
                        onSubmitDeck()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .width(300.dp)
                        .padding(bottom = 25.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = AppTheme.neutral500,
                        containerColor = md_theme_light_primary
                    )
                ) {
                    Text(
                        text = submitButtonText, color = Color.White
                    )
                }
            }

        }
    }
}