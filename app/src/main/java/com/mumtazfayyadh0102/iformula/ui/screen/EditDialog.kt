package com.mumtazfayyadh0102.iformula.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mumtazfayyadh0102.iformula.R

@Composable
fun EditDialog(
    imageUrl: String,
    titleInit: String,
    descriptionInit: String,
    onDismissRequest: () -> Unit,
    onUpdate: (String, String) -> Unit
) {

    val borderColor = if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) {
        Color.White
    } else {
        MaterialTheme.colorScheme.primary
    }

    var title by remember { mutableStateOf(titleInit) }
    var description by remember { mutableStateOf(descriptionInit) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.update_message),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photos",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.baseline_broken_image_24),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = stringResource(R.string.title), color = if (borderColor == Color.White) Color.White else Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = stringResource(R.string.description), color = if (borderColor == Color.White) Color.White else Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onUpdate(title, description)
                onDismissRequest()
            }) {
                Text(
                    text = stringResource(R.string.update_confirm), color = Color.hsl(hue = 207f, saturation = 0.64f, lightness = 0.59f)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}


