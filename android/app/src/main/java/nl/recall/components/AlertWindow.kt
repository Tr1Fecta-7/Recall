package nl.recall.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.recall.theme.AppTheme

@Composable
fun AlertWindow(
    title: String,
    subText: String,
    confirmText: String,
    confirmTextColor: Color,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    onPressConfirm: () -> Unit,
    cancelButtonVisibility: Boolean = true
) {
    MaterialTheme {
        Column {
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = {
                        onCloseDialog()
                    },
                    title = {
                        Text(
                            title,
                            color = AppTheme.neutral800,
                        )
                    },
                    text = {
                        Text(
                            subText,
                            color = AppTheme.neutral800,
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onPressConfirm()
                                onCloseDialog()
                            }) {
                            Text(
                                confirmText,
                                color = confirmTextColor
                            )
                        }
                    },
                    dismissButton = {
                        if(cancelButtonVisibility) {
                            TextButton(
                                onClick = {
                                    onCloseDialog()
                                }) {
                                Text(
                                    "Cancel",
                                    color = AppTheme.neutral800
                                )
                            }
                        }

                    }
                )
            }
        }

    }
}