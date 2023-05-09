package nl.recall.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import nl.recall.theme.AppTheme

@Composable
fun ColorPickerWindow(
    confirmText: String,
    confirmTextColor: Color,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    onSelectColor: (color: String) -> Unit,
    preSelectedColor: String
) {
    var selectedColor by remember {
        mutableStateOf(preSelectedColor)
    }
    val controller = rememberColorPickerController()
    MaterialTheme {
        Column {

            if (openDialog) {

                AlertDialog(
                    onDismissRequest = {
                        onCloseDialog()
                    },
                    containerColor = AppTheme.neutral50,
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            HsvColorPicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(10.dp),
                                controller = controller,
                                onColorChanged = { colorEnvelope: ColorEnvelope ->
                                    selectedColor = "#${colorEnvelope.hexCode}"
                                },
                                initialColor = AppTheme.neutral100
                            )
                            Box(modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = Color(selectedColor.toColorInt()))
                                .width(200.dp)
                                .height(50.dp)
                            )
                        }

                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onSelectColor(selectedColor)
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