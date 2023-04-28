package nl.recall.errorScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.recall.R
import nl.recall.theme.AndroidAppTheme
import nl.recall.theme.AppTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ErrorScreen(titleText: String, errorText: String, navigator: DestinationsNavigator) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = AppTheme.white
            ),
            title = {
                Text(
                    text = titleText
                )
            },
            navigationIcon = {
                IconButton(onClick = {navigator.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "go back")
                }
            },

            )
    },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.width(250.dp),
                    painter = painterResource(id = R.drawable.error_image),
                    contentDescription = "error"
                )
                Text(text = errorText)
            }


        }

    )
}
