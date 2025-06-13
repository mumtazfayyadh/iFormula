package com.mumtazfayyadh0102.iformula.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun CircuitScreen(navController: NavController) {
    val appBarColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    val dataStore = UserDataStore.getInstance(context)

    val user by dataStore.userFlow.collectAsState(User())

    var showDialog by remember { mutableStateOf(false) }

    val circuitResources = listOf(
        Pair(1, R.string.circuit_bahrain),
        Pair(2, R.string.circuit_uk),
        Pair(3, R.string.circuit_hungary),
        Pair(4, R.string.circuit_saudi),
        Pair(5, R.string.circuit_australia),
        Pair(6, R.string.circuit_japan),
        Pair(7, R.string.circuit_china),
        Pair(8, R.string.circuit_miami),
        Pair(9, R.string.circuit_usa_miami),
        Pair(10, R.string.circuit_monaco),
        Pair(11, R.string.circuit_canada),
        Pair(12, R.string.circuit_spain),
        Pair(13, R.string.circuit_austria),
        Pair(14, R.string.circuit_belgium),
        Pair(15, R.string.circuit_netherlands),
        Pair(16, R.string.circuit_italy),
        Pair(17, R.string.circuit_azerbaijan),
        Pair(18, R.string.circuit_singapore),
        Pair(19, R.string.circuit_usa_austin),
        Pair(20, R.string.circuit_mexico),
        Pair(21, R.string.circuit_brazil),
        Pair(22, R.string.circuit_vegas),
        Pair(23, R.string.circuit_qatar),
        Pair(24, R.string.circuit_abu_dhabi)
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.circuit_schedule),
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
                text = stringResource(id = R.string.circuit_schedule),
                fontSize = 28.sp
            )

            Image(
                painter = painterResource(id = R.drawable.circuit_schedule),
                contentDescription = "Circuit Schedule",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.circuit_list_title),
                fontSize = 28.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            circuitResources.forEach { (number, resourceId) ->
                CircuitItem(number, stringResource(id = resourceId))
            }
        }
    }
}

@Composable
fun CircuitItem(number: Int, circuitText: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$number. ",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = circuitText,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CircuitScreenPreview() {
    CircuitScreen(navController = NavController(LocalContext.current))
}