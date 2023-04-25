package nl.recall.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import nl.recall.theme.AppTheme

@Composable
fun ColorPickerWindow(
    title: String,
    subText: String,
    confirmText: String,
    confirmTextColor: Color,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    onPressConfirm: () -> Unit,
    onSelectColor: (color: ColorEnvelope) -> Unit
) {
    val controller = rememberColorPickerController()
    MaterialTheme {
        Column {

            if (openDialog) {

                AlertDialog(
                    onDismissRequest = {
                        onCloseDialog()
                    },
                    title = {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = { colorEnvelope: ColorEnvelope ->
                                onSelectColor(colorEnvelope)
                            },
                            initialColor = AppTheme.neutral100
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
                )
            }
        }

    }
}