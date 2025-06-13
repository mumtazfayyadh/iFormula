package com.mumtazfayyadh0102.iformula.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.User
import com.mumtazfayyadh0102.iformula.navigation.Screen
import com.mumtazfayyadh0102.iformula.network.UserDataStore
import com.mumtazfayyadh0102.iformula.util.signIn
import com.mumtazfayyadh0102.iformula.util.signOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverScreen(navController: NavController) {
    val appBarColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    val dataStore = UserDataStore.getInstance(context)
    val user by dataStore.userFlow.collectAsState(User())

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.drivers_teams),
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
    ) { innerPadding ->
        if (showDialog) {
            ProfileDialog(
                user = user,
                onDismissRequest = { showDialog = false }) {
                CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore) }
                showDialog = false
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.meet_teams_drivers),
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Intro
            Text(
                text = stringResource(id = R.string.meet_teams_drivers_desc),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tim 1: Mercedes
            TeamSection(
                teamNumber = 1,
                teamName = stringResource(id = R.string.mercedes),
                driver1 = stringResource(id = R.string.mercedes_driver_1),
                driver2 = stringResource(id = R.string.mercedes_driver_2)
            )

            // Tim 2: Red Bull
            TeamSection(
                teamNumber = 2,
                teamName = stringResource(id = R.string.redbull),
                driver1 = stringResource(id = R.string.redbull_driver_1),
                driver2 = stringResource(id = R.string.redbull_driver_2)
            )

            // Tim 3: Ferrari
            TeamSection(
                teamNumber = 3,
                teamName = stringResource(id = R.string.ferrari),
                driver1 = stringResource(id = R.string.ferrari_driver_1),
                driver2 = stringResource(id = R.string.ferrari_driver_2)
            )

            // Tim 4: McLaren
            TeamSection(
                teamNumber = 4,
                teamName = stringResource(id = R.string.mclaren),
                driver1 = stringResource(id = R.string.mclaren_driver_1),
                driver2 = stringResource(id = R.string.mclaren_driver_2)
            )

            // Tim 5: Aston Martin
            TeamSection(
                teamNumber = 5,
                teamName = stringResource(id = R.string.aston_martin),
                driver1 = stringResource(id = R.string.aston_martin_driver_1),
                driver2 = stringResource(id = R.string.aston_martin_driver_2)
            )

            // Tim 6: Alpine
            TeamSection(
                teamNumber = 6,
                teamName = stringResource(id = R.string.alpine),
                driver1 = stringResource(id = R.string.alpine_driver_1),
                driver2 = stringResource(id = R.string.alpine_driver_2)
            )

            // Tim 7: Williams
            TeamSection(
                teamNumber = 7,
                teamName = stringResource(id = R.string.williams),
                driver1 = stringResource(id = R.string.williams_driver_1),
                driver2 = stringResource(id = R.string.williams_driver_2)
            )

            // Tim 8: RB
            TeamSection(
                teamNumber = 8,
                teamName = stringResource(id = R.string.rb),
                driver1 = stringResource(id = R.string.rb_driver_1),
                driver2 = stringResource(id = R.string.rb_driver_2)
            )

            // Tim 9: Sauber
            TeamSection(
                teamNumber = 9,
                teamName = stringResource(id = R.string.sauber),
                driver1 = stringResource(id = R.string.sauber_driver_1),
                driver2 = stringResource(id = R.string.sauber_driver_2)
            )

            // Tim 10: Haas
            TeamSection(
                teamNumber = 10,
                teamName = stringResource(id = R.string.haas),
                driver1 = stringResource(id = R.string.haas_driver_1),
                driver2 = stringResource(id = R.string.haas_driver_2)
            )

            // Teks penutup
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.teams_conclusion),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun TeamSection(
    teamNumber: Int,
    teamName: String,
    driver1: String,
    driver2: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Nomor dan Nama Tim
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$teamNumber. ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = teamName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Driver 1
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "• ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = driver1,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Driver 2
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "• ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = driver2,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DriverScreenPreview() {
    DriverScreen(navController = NavController(LocalContext.current))
}