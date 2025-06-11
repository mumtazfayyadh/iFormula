package com.mumtazfayyadh0102.iformula.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun WhatIsF1Screen(navController: NavController) {
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
                            text = stringResource(id = R.string.what_is_f1),
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
            // Judul F1
            Text(
                text = stringResource(id = R.string.what_is_f1),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Penjelasan F1
            Text(
                text = stringResource(id = R.string.f1_explanation),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Penjelasan teknologi F1
            Text(
                text = stringResource(id = R.string.f1_technology_explanation),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Judul Aturan F1
            Text(
                text = stringResource(id = R.string.f1_rules),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Aturan Umum F1
            Text(
                text = stringResource(id = R.string.general_f1_rules),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Daftar Aturan dengan Bullet Points
            BulletPoint(text = stringResource(id = R.string.rule_teams_drivers))
            BulletPoint(text = stringResource(id = R.string.rule_race_length))
            BulletPoint(text = stringResource(id = R.string.rule_qualifying))
            BulletPoint(text = stringResource(id = R.string.rule_tire_change))
            BulletPoint(text = stringResource(id = R.string.rule_points))
            BulletPoint(text = stringResource(id = R.string.rule_fastest_lap))
            BulletPoint(text = stringResource(id = R.string.rule_car_upgrade))
            BulletPoint(text = stringResource(id = R.string.rule_fuel_weight))
            BulletPoint(text = stringResource(id = R.string.rule_engines))
            BulletPoint(text = stringResource(id = R.string.rule_penalties))
            BulletPoint(text = stringResource(id = R.string.rule_drs))

            Spacer(modifier = Modifier.height(24.dp))

            // Judul Ban F1
            Text(
                text = stringResource(id = R.string.tire_cars),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Gambar Ban Pirelli
            Image(
                painter = painterResource(id = R.drawable.tires),
                contentDescription = "Tires",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            // Daftar Jenis Ban
            Text(
                text = stringResource(id = R.string.tire_c1),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.tire_c2),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.tire_c3),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.tire_c4),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.tire_c5),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BulletPoint(text: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "• ",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview (showBackground = true)
@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WhatIsF1ScreenPreview() {
    WhatIsF1Screen(navController = NavController(LocalContext.current))
}