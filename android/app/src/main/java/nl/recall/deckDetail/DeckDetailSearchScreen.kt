package nl.recall.deckDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import nl.recall.R
import nl.recall.theme.AppTheme


//@RootNavGraph(start = true)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DeckDetailSearchScreen() {
    val list: List<String> = listOf("boe", "bah", "", "")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                ),
                title = {
                    Text(text = stringResource(id = R.string.card_searchbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                    }
                },
            )
        },

        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
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
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(
                        items = list,
                        itemContent = {
                            Card(
                                onClick = { /* */ },
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, AppTheme.neutral200),
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .background(AppTheme.white)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.start_learning_text),
                                            color = AppTheme.neutral800,
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                                        contentDescription = "arrow right",
                                        tint = AppTheme.neutral800
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    )


}