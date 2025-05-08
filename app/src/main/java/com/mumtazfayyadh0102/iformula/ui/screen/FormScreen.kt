package com.mumtazfayyadh0102.iformula.ui.screen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.findFirstRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    var driverName by rememberSaveable { mutableStateOf("") }
    var lapTime by rememberSaveable { mutableStateOf("") }
    var startPos by rememberSaveable { mutableStateOf("") }
    var points by rememberSaveable { mutableStateOf("") }

    val scope = rememberCoroutineScope()


    // Jika sedang edit, ambil data berdasarkan ID
    LaunchedEffect(noteId) {
        noteId?.let {
            val note = viewModel.getNoteById(it)
            note?.let {
                driverName = it.driverName
                lapTime = it.bestLapTime
                startPos = it.startPosition.toString()
                points = it.points.toString()
            }
        }
    }
    val appBarColor = if (isSystemInDarkTheme()) DarkF1Black else LightF1Red
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = if (noteId == null) stringResource(id = R.string.add) else stringResource(id = R.string.edit),
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
            FloatingActionButton(onClick = {
                // Validasi input
                if (driverName.isBlank() || lapTime.isBlank() || startPos.isBlank() || points.isBlank()) {
                    Toast.makeText(context, context.getString(R.string.form_required), Toast.LENGTH_SHORT).show()
                } else {
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
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Simpan")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
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
                onValueChange = { lapTime = it },
                label = { stringResource(id = R.string.label_lap_time) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = startPos,
                onValueChange = { startPos = it.filter { char -> char.isDigit() } },
                label = { Text(stringResource(id = R.string.label_start_pos)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = points,
                onValueChange = { points = it.filter { char -> char.isDigit() } },
                label = { Text(stringResource(id = R.string.label_points)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}