package nl.recall.createdeck
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.domain.models.DeckData
import nl.recall.theme.AppTheme
import nl.recall.theme.md_theme_light_primary

@Destination
@Composable
fun DeckCreate(navigator: DestinationsNavigator){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(stringResource(id = R.string.create_deck_title))},
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                ),
            )
        },
        content = { MainContent(navigator = navigator, it)}
    )
}

@Composable
private fun MainContent(navigator: DestinationsNavigator, paddingValues: PaddingValues){
    var createTextField by remember { mutableStateOf(TextFieldValue("")) }
    var newDeck: DeckData = DeckData("")
    var iconTextField by remember { mutableStateOf(TextFieldValue(newDeck.emoji)) }
//    iconTextField = TextFieldValue(newDeck.emoji)
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 60.dp)
                .weight(1f)
        ) {
            OutlinedTextField(
                value = createTextField,
                label = { Text(text = stringResource(id = R.string.create_deck_create_text_field))},
                onValueChange = { newText ->
                    createTextField = newText
                },
                placeholder = { Text(text = stringResource(id = R.string.create_deck_create_text_field_placeholder))},
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
                    .background(Color(newDeck.backgroundColor.toColorInt())),
            ) {
                    TextField(
                        value = iconTextField,
                        onValueChange = { newText ->
                            if(newText.text.length <= 2 && newText.text.length % 2 == 0){
                                iconTextField = newText
                            }
                        },
                        modifier = Modifier.background(color = Color.White),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 155.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(newDeck.backgroundColor.toColorInt()),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),

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
                onClick = {

                },
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(md_theme_light_primary)
                    .width(300.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.create_deck_title),
                    color = Color.White
                )
            }
        }
    }

}