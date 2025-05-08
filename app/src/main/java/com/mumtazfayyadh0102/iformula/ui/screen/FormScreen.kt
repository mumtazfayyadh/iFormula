package com.mumtazfayyadh0102.iformula.ui.screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.RaceNotes
import com.mumtazfayyadh0102.iformula.ui.theme.DarkF1Black
import com.mumtazfayyadh0102.iformula.ui.theme.LightF1Red
import com.mumtazfayyadh0102.iformula.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController, noteId: Int?) {

    val context = LocalContext.current
    val viewModel: RaceNotesViewModel = viewModel(factory = ViewModelFactory(context))

    val appBarColor = if (isSystemInDarkTheme()) DarkF1Black else LightF1Red
    var showDialog by rememberSaveable { mutableStateOf(false) }

    var driverName by rememberSaveable { mutableStateOf("") }
    var lapTime by rememberSaveable { mutableStateOf("") }
    var startPos by rememberSaveable { mutableStateOf("") }
    var points by rememberSaveable { mutableStateOf("") }

    var lapTimeError by rememberSaveable { mutableStateOf(false) }
    var startPosError by rememberSaveable { mutableStateOf(false) }
    var pointsError by rememberSaveable { mutableStateOf(false) }


    // Jika sedang edit, ambil data berdasarkan ID
    LaunchedEffect(noteId) {
        noteId?.let { id ->
            val note = viewModel.getNoteById(id)
            note?.let { data ->
                driverName = data.driverName
                lapTime = data.bestLapTime
                startPos = data.startPosition.toString()
                points = data.points.toString()
            }
        }

    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = if (noteId == null) stringResource(id = R.string.add) else stringResource(
                                id = R.string.edit
                            ),
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarColor,
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        if (noteId != null) {
                            IconButton(onClick = { showDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )

                HorizontalDivider(
                    color = Color.White,
                    thickness = 1.dp
                )
            }
            if (showDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.delete_title)) },
                    text = { Text(stringResource(R.string.delete_message)) },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.delete(
                                RaceNotes(
                                    id = noteId ?: 0,
                                    driverName = driverName,
                                    bestLapTime = lapTime,
                                    startPosition = startPos.toIntOrNull() ?: 0,
                                    points = points.toIntOrNull() ?: 0
                                )
                            )
                            showDialog = false
                            navController.popBackStack()
                        }) {
                            Text(stringResource(R.string.delete_confirm))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(stringResource(R.string.delete_cancel))
                        }
                    }
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = appBarColor,
                onClick = {
                // Regex format lap time: 1.23.456
                val lapTimeRegex = Regex("^\\d\\.[0-5]\\d\\.\\d{3}$")

                // Cek apakah input valid
                lapTimeError = !lapTimeRegex.matches(lapTime)
                startPosError = startPos.toIntOrNull()?.let { it !in 1..20 } ?: true
                pointsError = points.toIntOrNull()?.let { it !in 0..25 } ?: true

                val fieldBlank =
                    driverName.isBlank() || lapTime.isBlank() || startPos.isBlank() || points.isBlank()
                val hasError = lapTimeError || startPosError || pointsError || fieldBlank

                if (hasError) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.form_required),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@FloatingActionButton
                }

                // Buat catatan dan simpan
                val note = RaceNotes(
                    id = noteId ?: 0,
                    driverName = driverName,
                    bestLapTime = lapTime,
                    startPosition = startPos.toInt(),
                    points = points.toInt()
                )

                if (noteId == null) viewModel.insert(note)
                else viewModel.update(note)

                navController.popBackStack()
            }) {
                Icon(Icons.Default.Check, tint = Color.White, contentDescription = stringResource(R.string.form_save))
            }
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = driverName,
                onValueChange = { driverName = it },
                label = { Text(stringResource(id = R.string.label_driver)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = lapTime,
                onValueChange = {
                    lapTime = it
                    lapTimeError = false
                },
                label = { Text(stringResource(R.string.label_lap_time)) },
                supportingText = {
                    if (lapTimeError) {
                        Text(
                            text = stringResource(R.string.error_lap_time),
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text(stringResource(R.string.lap_time_hint))
                    }
                },
                isError = lapTimeError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = startPos,
                onValueChange = {
                    startPos = it.filter { c -> c.isDigit() }
                    startPosError = false
                },
                label = { Text(stringResource(R.string.label_finish_pos)) },
                supportingText = {
                    if (startPosError) {
                        Text(stringResource(R.string.error_start_pos), color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(stringResource(R.string.start_pos_hint))
                    }
                },
                isError = startPosError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = points,
                onValueChange = {
                    points = it.filter { c -> c.isDigit() }
                    pointsError = false
                },
                label = { Text(stringResource(R.string.label_points)) },
                supportingText = {
                    if (pointsError) {
                        Text(stringResource(R.string.error_points), color = MaterialTheme.colorScheme.error)
                    } else {
                        Text(stringResource(R.string.points_hint))
                    }
                },
                isError = pointsError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}