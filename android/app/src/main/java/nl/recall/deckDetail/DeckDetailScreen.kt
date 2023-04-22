package nl.recall.detail

import DeckDetailPreview
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.destinations.DeckDetailSearchScreenDestination
import nl.recall.presentation.deckDetail.DeckDetailViewModel
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Destination
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DeckDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: DeckDetailViewModel = koinViewModel(parameters = {
        parametersOf(DeckDetailViewModelArgs(1))
    })
) {
    val list: List<String> = listOf("boe", "", "", "")
    var expanded by remember { mutableStateOf(false) }


    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppTheme.white
        ), title = {
            Text(
                text = viewModel.deck.value.data?.title
                    ?: stringResource(id = R.string.placeholder_title)
            )
        }, navigationIcon = {
            IconButton(onClick = { navigator.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
            }
        }, actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(AppTheme.white)
                    .width(180.dp),
            ) {
                DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_edit_deck)) },
                    onClick = { /* Handle edit deck! */ })
                DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_delete_deck)) },
                    onClick = { /* Handle send delete eck! */ })
            }
        }

        )
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            DeckDetailPreview()
            Text(
                modifier = Modifier.padding(top = 15.dp, bottom = 10.dp),
                text = stringResource(id = R.string.cards_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.neutral200,
                ),
                shape = RoundedCornerShape(35.dp),
                onClick = {
                    navigator.navigate(DeckDetailSearchScreenDestination)
                }) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.search_bar_card_hint),
                        color = AppTheme.neutral500
                    )
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }
            }

            LazyColumn(
                modifier = Modifier.padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(items = list, itemContent = {
                    Card(
                        onClick = { /* */ },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, AppTheme.neutral200),
                        modifier = Modifier.fillMaxWidth()
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
                                    text = stringResource(id = R.string.placeholder_card_front),
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
                })
            }
        }

    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add card")
        }
    })
}