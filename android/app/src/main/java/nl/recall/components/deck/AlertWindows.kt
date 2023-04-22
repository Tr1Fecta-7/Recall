package nl.recall.components.deck

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertWindow(title: String, subText: String, openDialog: Boolean, closeDialog: () -> Unit, onPressConfirm: () -> Unit) {
    MaterialTheme {
        Column {

            if (openDialog) {

                AlertDialog(
                    onDismissRequest = {
                        closeDialog()
                    },
                    title = {
                        Text(title)
                    },
                    text = {
                        Text(subText)
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onPressConfirm()
                                closeDialog()
                            }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                closeDialog()
                            }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }

    }
}