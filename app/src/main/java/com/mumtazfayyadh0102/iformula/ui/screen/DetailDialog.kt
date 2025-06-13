package com.mumtazfayyadh0102.iformula.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mumtazfayyadh0102.iformula.R

@Composable
fun DetailDialog(
    imageUrl: String,
    title: String,
    description: String,
    onDismissRequest: () -> Unit
) {
    val borderColor = if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) {
        Color.White
    } else {
        MaterialTheme.colorScheme.primary
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = null,
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Detail",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.baseline_broken_image_24),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.title), color = if (borderColor == Color.White) Color.White else Color.Gray) },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.description), color = if (borderColor == Color.White) Color.White else Color.Gray)  },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor
                    ),
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.close), color = Color.Red)
            }
        },
        dismissButton = {}
    )
}