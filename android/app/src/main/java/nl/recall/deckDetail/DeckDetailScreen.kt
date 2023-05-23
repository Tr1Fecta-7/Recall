package nl.recall.deckDetail

import DeckDetailPreview
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.components.AlertWindow
import nl.recall.components.BottomNav
import nl.recall.components.ImageMessage
import nl.recall.destinations.CreateCardScreenDestination
import nl.recall.destinations.DeckDetailSearchScreenDestination
import nl.recall.destinations.DeckEditDestination
import nl.recall.destinations.DecksOverviewScreenDestination
import nl.recall.destinations.EditCardScreenDestination
import nl.recall.destinations.RepeatStudyDeckScreenDestination
import nl.recall.destinations.StudyDeckScreenDestination
import nl.recall.domain.deck.model.DeckWithCards
import nl.recall.presentation.deckDetail.DeckDetailViewModel
import nl.recall.presentation.deckDetail.model.DeckDetailViewModelArgs
import nl.recall.presentation.uiState.UIState
import nl.recall.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date

@Destination
@Composable
fun DeckDetailScreen(
    deckId: Long,
    navController: NavController,
    navigator: DestinationsNavigator,
    viewModel: DeckDetailViewModel = koinViewModel(parameters = {
        parametersOf(DeckDetailViewModelArgs(deckId))
    })
) {
    viewModel.observeDeck()
    val uiState by viewModel.state.collectAsState()
    val deckWithCards by viewModel.deck.collectAsState()
    val isDeckDeleted by viewModel.isDeckDeleted.collectAsState()
    var expandedMoreVert by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    val navigateToCreateCard: (Long) -> Unit =
        { navigator.navigate(CreateCardScreenDestination(it)) }
    val publishDeckState by viewModel.publishDeckState.collectAsState()
    val context = LocalContext.current

    if (publishDeckState == UIState.ERROR) {
        informUser(context, stringResource(id = R.string.publish_deck_error))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.neutral50
                ), title = {
                    Text(
                        text = deckWithCards?.deck?.title
                            ?: stringResource(id = R.string.deck_name_title)
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                    }
                }, actions = {
                    IconButton(onClick = {
                        if (uiState == UIState.NORMAL) {
                            expandedMoreVert = !expandedMoreVert
                        }
                    }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = expandedMoreVert,
                        onDismissRequest = { expandedMoreVert = false },
                        modifier = Modifier
                            .background(AppTheme.white)
                            .width(180.dp),
                    ) {
                        DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_edit_deck)) },
                            onClick = {
                                navigator.navigate(
                                    DeckEditDestination(
                                        deckWithCards?.deck?.id ?: 0L
                                    )
                                )
                            })
                        DropdownMenuItem(
                            trailingIcon = {
                                if (publishDeckState == UIState.LOADING) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else if (publishDeckState == UIState.NORMAL) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        tint = AppTheme.primary500,
                                        contentDescription = "successfully published"
                                    )
                                }
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.dropdown_menu_publish_deck),
                                    color = if (publishDeckState == UIState.LOADING) {
                                        AppTheme.neutral300
                                    } else {
                                        Color.Unspecified
                                    }
                                )
                            },
                            onClick = {
                                if (publishDeckState != UIState.LOADING) {
                                    viewModel.postDeck()
                                }
                            }
                        )
                        DropdownMenuItem(text = { Text(stringResource(id = R.string.dropdown_menu_delete_deck)) },
                            onClick = { openDialog = true })
                    }
                }

            )
        }, bottomBar = {
            BottomNav(navController = navController)
        },
        containerColor = AppTheme.neutral50,

        content = {
            when (uiState) {
                UIState.NORMAL -> {
                    if (isDeckDeleted) {
                        navigator.navigate(DecksOverviewScreenDestination)
                    } else {
                        deckWithCards?.let { deck ->
                            Column(
                                modifier = Modifier
                                    .padding(it)
                                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                                    .fillMaxSize()
                            ) {
                                Content(
                                    deckWithCards = deck,
                                    navigator = navigator,
                                    viewModel = viewModel,
                                    openDialog = openDialog,
                                    closeDialog = {
                                        openDialog = it
                                        expandedMoreVert = it
                                    }
                                )
                            }

                        }
                    }
                }

                UIState.ERROR -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImageMessage(
                            painter = painterResource(id = R.drawable.error_image),
                            text = stringResource(id = R.string.no_deck_found)
                        )
                    }
                }

                UIState.LOADING -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                UIState.EMPTY -> {

                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiState == UIState.NORMAL) {
                        navigateToCreateCard(deckWithCards?.deck?.id ?: 0L)
                    }
                },
                contentColor = AppTheme.primary900,
                containerColor = AppTheme.primary300
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add card")
            }
        })
}

@Composable
private fun Content(
    deckWithCards: DeckWithCards,
    navigator: DestinationsNavigator,
    viewModel: DeckDetailViewModel,
    openDialog: Boolean,
    closeDialog: (Boolean) -> (Unit)
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        DeckDetailPreview(
            deckWithCards = deckWithCards,
            title = stringResource(id = R.string.start_smart_learning_text),
            icon = painterResource(id = R.drawable.cards_icon),
            cardCount = deckWithCards.cards.stream().filter{it.dueDate <= Date() }.count(),
            onClick = {
                navigator.navigate(StudyDeckScreenDestination(deckWithCards.deck.id))
            }
        )

        DeckDetailPreview(
            deckWithCards = deckWithCards,
            title = stringResource(id = R.string.start_learning_text),
            icon = painterResource(id = R.drawable.repeat_study_icon),
            cardCount = deckWithCards.cards.size.toLong(),
            onClick = {
                navigator.navigate(RepeatStudyDeckScreenDestination(deckWithCards.deck.id))
            }
        )

        Text(
            text = stringResource(id = R.string.cards_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (deckWithCards.cards.isNotEmpty()) {
            Card(modifier = Modifier
                .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.neutral200,
                ),
                shape = RoundedCornerShape(35.dp),
                onClick = {
                    navigator.navigate(DeckDetailSearchScreenDestination(deckWithCards.deck.id))
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
        }
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        itemsIndexed(items = deckWithCards.cards, itemContent = { index, card ->

            AnimatedVisibility(
                visibleState = state,
                enter = slideInVertically(
                    initialOffsetY = { offset -> offset + 20 },
                    animationSpec = tween(
                        durationMillis = (index * 105)
                    )
                ),
                exit = fadeOut()
            ) {
                Card(
                    onClick = {
                        navigator.navigate(
                            EditCardScreenDestination(
                                clickedCardId = card.id,
                                deckId = card.deckId
                            )
                        )
                    },
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
                                text = card.front,
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
        })
    }


    AlertWindow(
        title = stringResource(id = R.string.dialog_delete_deck_title),
        subText = stringResource(id = R.string.dialog_delete_deck_text),
        confirmText = stringResource(id = R.string.delete_text),
        confirmTextColor = AppTheme.red700,
        openDialog = openDialog,
        onCloseDialog = {
            closeDialog(false)
        },
        onPressConfirm = {
            viewModel.deleteDeckById(deckWithCards)
        }
    )
}


private fun informUser(context: Context, text: String) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}