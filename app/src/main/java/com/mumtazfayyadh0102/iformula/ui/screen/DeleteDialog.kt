package com.mumtazfayyadh0102.iformula.ui.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mumtazfayyadh0102.iformula.R

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = { Text(text = stringResource(id = R.string.delete_message)) },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.delete_confirm), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.delete_cancel), color = Color.hsl(hue = 207f, saturation = 0.64f, lightness = 0.59f))
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}