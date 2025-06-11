package com.mumtazfayyadh0102.iformula.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.RaceNotes
import com.mumtazfayyadh0102.iformula.model.User
import com.mumtazfayyadh0102.iformula.navigation.Screen
import com.mumtazfayyadh0102.iformula.network.UserDataStore
import com.mumtazfayyadh0102.iformula.util.SettingsDataStore
import com.mumtazfayyadh0102.iformula.util.signIn
import com.mumtazfayyadh0102.iformula.util.signOut
import com.mumtazfayyadh0102.iformula.viewmodel.RaceNotesViewModel
import com.mumtazfayyadh0102.iformula.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = UserDataStore.getInstance(context)

    val user by dataStore.userFlow.collectAsState(User())

    var showDialog by remember { mutableStateOf(false) }

    val settings = remember { SettingsDataStore(context) }
    val isListLayout by settings.layoutFlow.collectAsState(initial = true)
    val scope = rememberCoroutineScope()
    val viewModel: RaceNotesViewModel = viewModel(factory = ViewModelFactory(context))
    val notes = viewModel.raceNotes.collectAsState(initial = emptyList())
    if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) Color.White else MaterialTheme.colorScheme.primary


    val appBarColor = MaterialTheme.colorScheme.primary
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = appBarColor,
                onClick = {
                // navigasi ke Form tanpa ID (tambah baru)
                navController.navigate(Screen.Form.route)
            }) {
                Icon(Icons.Default.Add, tint = Color.White, contentDescription = "Tambah")
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
                        IconButton(onClick = {
                            scope.launch {
                                settings.saveLayout(!isListLayout)
                            }
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isListLayout)
                                        R.drawable.baseline_grid_view_24
                                    else
                                        R.drawable.baseline_view_list_24
                                ),
                                contentDescription = if (isListLayout) "Grid View" else "List View",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = {
                            if (user.email.isEmpty()) {
                                CoroutineScope(Dispatchers.IO).launch { signIn(context, dataStore) }
                            } else {
                                showDialog = true
                            }
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_account_circle_24),
                                contentDescription = stringResource(R.string.profil),
                                tint = Color.White
                            )
                        }

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
        if (showDialog) {
            ProfileDialog(
                user = user,
                onDismissRequest = { showDialog = false }) {
                CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore) }
                showDialog = false
            }
        }

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
            if (isListLayout) {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(notes.value) { note ->
                        NoteItem(note = note, onClick = {
                            navController.navigate(Screen.Form.withId(note.id))
                        })
                    }
                }
            } else {
                androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                    columns = androidx.compose.foundation.lazy.grid.GridCells.Adaptive(minSize = 160.dp),
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    items(notes.value) { note ->
                        GridItem(note = note, onClick = {
                            navController.navigate(Screen.Form.withId(note.id))
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: RaceNotes, onClick: () -> Unit) {
    val borderColor = if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) Color.White else MaterialTheme.colorScheme.primary

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(note.driverName, style = MaterialTheme.typography.titleMedium)
            Text("${stringResource(R.string.label_lap_time)}: ${note.bestLapTime}")
            Text("${stringResource(R.string.label_finish_pos)}: ${note.startPosition}")
            Text("${stringResource(R.string.label_points)}: ${note.points}")
        }
    }
}


@Composable
fun GridItem(note: RaceNotes, onClick: () -> Unit) {
    val borderColor = if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) Color.White else MaterialTheme.colorScheme.primary

    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(note.driverName, style = MaterialTheme.typography.titleMedium)
            Text("${stringResource(R.string.label_lap_time)}: ${note.bestLapTime}")
            Text("${stringResource(R.string.label_finish_pos)}: ${note.startPosition}")
            Text("${stringResource(R.string.label_points)}: ${note.points}")
        }
    }
}
