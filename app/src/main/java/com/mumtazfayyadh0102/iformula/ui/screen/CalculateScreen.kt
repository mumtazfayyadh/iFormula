package com.mumtazfayyadh0102.iformula.ui.screen

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.Circuit
import com.mumtazfayyadh0102.iformula.model.User
import com.mumtazfayyadh0102.iformula.navigation.Screen
import com.mumtazfayyadh0102.iformula.network.UserDataStore
import com.mumtazfayyadh0102.iformula.util.signIn
import com.mumtazfayyadh0102.iformula.util.signOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(navController: NavController) {
    val appBarColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    val dataStore = UserDataStore.getInstance(context)

    val user by dataStore.userFlow.collectAsState(User())

    var showDialog by remember { mutableStateOf(false) }

    val borderColor = if (MaterialTheme.colorScheme.primary == Color(0xFF121212)) {
        Color.White
    } else {
        MaterialTheme.colorScheme.primary
    }

    val circuits = listOf(
        Circuit("Monza", 5.793),
        Circuit("SPA", 7.004),
        Circuit("Monaco", 3.337),
        Circuit("Suzuka", 5.807),
        Circuit("Baku", 6.003),
        Circuit("Singapore", 4.940),
        Circuit("Silverstone", 5.831)
    )

    var selectedCircuitIndex by rememberSaveable { mutableIntStateOf(0)}
    val selectedCircuit = circuits[selectedCircuitIndex]
    var expanded by rememberSaveable { mutableStateOf(false) }
    var lapTimeText by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var calculationDone by rememberSaveable { mutableStateOf(false) }
    var averageSpeed by rememberSaveable { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.calculate),
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
                text = stringResource(id = R.string.calculate),
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.calculate_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = circuits[selectedCircuitIndex].name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.choose_circuit), color = if (borderColor == Color.White) Color.White else Color.Gray) },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow"
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = borderColor,
                        disabledBorderColor = borderColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    circuits.forEachIndexed { index, circuit ->
                        DropdownMenuItem(
                            text = { Text(circuit.name) },
                            onClick = {
                                selectedCircuitIndex = index
                                expanded = false
                                calculationDone = false
                            }
                        )
                    }
                }
            }

            // Input waktu lap
            OutlinedTextField(
                value = lapTimeText,
                onValueChange = {
                    lapTimeText = it
                    isError = false
                    calculationDone = false
                },
                label = { Text(stringResource(id = R.string.time_lap), color = if (borderColor == Color.White) Color.White else Color.Gray) },
                isError = isError,
                supportingText = {
                    Column {
                        Text(
                            text = "*" + stringResource(id = R.string.time_format_hint),
                            fontSize = 12.sp
                        )
                        if (isError) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    disabledBorderColor = borderColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Calculate
            Button(
                onClick = {
                    if (isValidTime(lapTimeText)) {
                        // Konversi input waktu lap ke detik
                        val lapTimeInSeconds = convertToSeconds(lapTimeText)
                        // Hitung kecepatan rata-rata (km/h) dengan Circuit yang dipilih
                        val circuitLength = selectedCircuit.length
                        val speedInKmh = calculateSpeed(circuitLength, lapTimeInSeconds)

                        averageSpeed = speedInKmh
                        calculationDone = true
                        isError = false
                    } else {
                        isError = true
                        val errorMessageResourceId = R.string.time_format_error
                        errorMessage = context.getString(errorMessageResourceId)
                        calculationDone = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = appBarColor,
                    contentColor = Color.White
                ),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Transparent
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(id = R.string.calculate_button))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Area Output
            if (calculationDone) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.output),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.average_speed),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = String.format(Locale.US, "%.0f", averageSpeed) + " km/hour!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = selectedCircuit.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Fun fact
                    Text(
                        text = stringResource(
                            id = R.string.fun_fact,
                            String.format(Locale.US, "%.0f", averageSpeed),
                            calculateTravelTime(averageSpeed)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Tombol Share
                    Button(

                        onClick = {
                            val shareText = context.getString(
                                R.string.share_result_text,
                                String.format(Locale.US, "%.0f", averageSpeed),
                                selectedCircuit.name

                            )
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_via)))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = appBarColor,
                            contentColor = Color.White
                        ),
                        border = BorderStroke(
                            width = 0.5.dp,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Transparent
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(stringResource(id = R.string.share_result))
                    }
                }
            }
        }
    }
}

// Fungsi validasi format waktu (m:ss.ms)
private fun isValidTime(time: String): Boolean {
    val regex = """^\d+.\d{2}\.\d{3}$""".toRegex() // Reguler Expression (Memastikan input sesuai)
    return regex.matches(time)
}

// Fungsi konversi format waktu (m:ss.ms) ke detik
private fun convertToSeconds(time: String): Double {
    val parts = time.split(".")
    val minutes = parts[0].toInt()
    val seconds = parts[1].toInt()
    val milliseconds = parts[2].toInt()

    return (minutes * 60.0) + seconds + milliseconds / 1000.0
}

// Fungsi untuk menghitung kecepatan rata-rata (km/h)
private fun calculateSpeed(distance: Double, timeInSeconds: Double): Double {
    // Kecepatan = jarak / waktu
    // Konversi ke km/h (distance = km, waktu = seconds)
    return distance / timeInSeconds * 3600.0
}

// Fungsi untuk menghitung estimasi waktu tempuh
private fun calculateTravelTime(speedKmh: Double, distanceKm: Double = 160.0): String {
    // Waktu dalam jam = jarak / kecepatan
    val timeHours = distanceKm / speedKmh

    // Konversi ke menit
    val totalMinutes = (timeHours * 60).toInt()

    // Format menjadi jam dan menit jika lebih dari 60 menit
    return if (totalMinutes >= 60) {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        "$hours jam $minutes menit"
    } else {
        "$totalMinutes menit"
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CalculateScreenPreview() {
    CalculateScreen(navController = NavController(LocalContext.current))
}