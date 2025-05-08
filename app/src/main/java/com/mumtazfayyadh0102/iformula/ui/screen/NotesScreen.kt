package com.mumtazfayyadh0102.iformula.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.RaceNotes
import com.mumtazfayyadh0102.iformula.navigation.Screen
import com.mumtazfayyadh0102.iformula.ui.theme.DarkF1Black
import com.mumtazfayyadh0102.iformula.ui.theme.LightF1Red
import com.mumtazfayyadh0102.iformula.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: RaceNotesViewModel = viewModel(factory = ViewModelFactory(context))
    val notes = viewModel.raceNotes.collectAsState(initial = emptyList())

    val appBarColor = if (isSystemInDarkTheme()) DarkF1Black else LightF1Red
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // navigasi ke Form tanpa ID (tambah baru)
                navController.navigate(Screen.Form.route)
            }) {
                Text("+")
            }
        },
        topBar = {
            Column {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.notes),
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
                        IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "About",
                                tint = Color.White
                            )
                        }
                    }
                )

                HorizontalDivider(
                    color = Color.White,
                    thickness = 1.dp
                )
            }
        }
    ) { padding ->
        if (notes.value.isEmpty()) {
            // Jika kosong, tampilkan pesan
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.notes_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(notes.value) { note ->
                    NoteItem(note = note, onClick = {
                        navController.navigate(Screen.Form.withId(note.id))
                    })
                }
            }
        }
    }
}

@Composable
    fun NoteItem(note: RaceNotes, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${stringResource(R.string.label_driver)} ${note.driverName}", style = MaterialTheme.typography.titleMedium)
            Text("${stringResource(R.string.label_lap_time)}: ${note.bestLapTime}")
            Text("${stringResource(R.string.label_start_pos)}: ${note.startPosition}")
            Text("${stringResource(R.string.label_points)}: ${note.points}")
        }
    }
}

