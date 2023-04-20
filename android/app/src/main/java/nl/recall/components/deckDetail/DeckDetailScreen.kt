package nl.recall.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import nl.recall.R
import nl.recall.components.deck.DeckPreview
import nl.recall.theme.AndroidAppTheme
import nl.recall.theme.AppTheme

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DeckDetailScreen() {
    val list: List<String> = listOf("boe", "bah", "", "")


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                ),
                title = {
                    Text(text = "title")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                }

            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),

            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AppTheme.white),
                        border = BorderStroke(1.dp, AppTheme.neutral400),
                        colors = CardDefaults.cardColors(
                            containerColor = AppTheme.white
                        ),
                        onClick = {
                            //To do
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(15.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(45.dp),
                                painter = painterResource(id = R.drawable.cards_icon),
                                contentDescription = "cards icon"
                            )

                            Column(
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .width(200.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.start_learning_text),

                                    )
                                Text(
                                    text = stringResource(id = R.string.amount_of_cards_in_deck),
                                    fontWeight = FontWeight.Light
                                )
                            }
                            Image(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = R.drawable.navigate_next),
                                contentDescription = "cards icon"
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                        text = stringResource(id = R.string.cards_title),
                        fontSize = 20.sp
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "textState",
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = AppTheme.neutral200,
                            cursorColor = Color.Black,
                            disabledLabelColor = AppTheme.white,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {

                        },
                        shape = RoundedCornerShape(35.dp),
                        singleLine = true,
                        trailingIcon = {
                            if (false) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "clear textfield"
                                    )
                                }
                            } else {
                                IconButton(
                                    modifier = Modifier.padding(end = 6.dp),
                                    onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "search"
                                    )
                                }
                            }
                        }
                    )
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    items(
                        items = list,
                        itemContent = {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppTheme.white)
                                    .padding(top = 2.dp),
                                border = BorderStroke(1.dp, AppTheme.neutral400),
                                colors = CardDefaults.cardColors(
                                    containerColor = AppTheme.white
                                ),
                                onClick = {
                                    //To do
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .width(250.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.start_learning_text)
                                            )
                                    }
                                    Image(
                                        modifier = Modifier.size(15.dp),
                                        painter = painterResource(id = R.drawable.navigate_next),
                                        contentDescription = "open card"
                                    )
                                }
                            }
                        }
                    )
                }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add card")
            }
        }
    )
}