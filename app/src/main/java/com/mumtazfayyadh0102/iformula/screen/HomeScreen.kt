package com.mumtazfayyadh0102.iformula.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.mumtazfayyadh0102.iformula.navigation.NavigationRoute
import com.mumtazfayyadh0102.iformula.ui.theme.DarkF1Black
import com.mumtazfayyadh0102.iformula.ui.theme.LightF1Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Deteksi mode tema
    val appBarColor = if (isSystemInDarkTheme()) DarkF1Black else LightF1Red

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = appBarColor,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { navController.navigate(NavigationRoute.ABOUT) }) {
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Welcoming
            Text(
                text = stringResource(id = R.string.welcome_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = stringResource(id = R.string.welcome_text),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // About F1
            Text(
                text = stringResource(id = R.string.what_is_f1),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // F1 Drivers Image
            Image(
                painter = painterResource(id = R.drawable.home_banner),
                contentDescription = "F1 Drivers",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { navController.navigate(NavigationRoute.WHAT_IS_F1) },
                contentScale = ContentScale.Crop
            )

            // Learn More Button
            Button(
                onClick = { navController.navigate(NavigationRoute.WHAT_IS_F1) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appBarColor,
                    contentColor = Color.White
                ),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Transparent
                )
            ) {
                Text(stringResource(id = R.string.learn_more))
            }

            // Menu Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Driver & Teams
                MenuCard(
                    icon = R.drawable.icon_driver,
                    title = stringResource(id = R.string.drivers_teams),
                    onClick = { navController.navigate(NavigationRoute.DRIVERS) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                // Gallery
                MenuCard(
                    icon = R.drawable.icon_gallery,
                    title = stringResource(id = R.string.gallery),
                    onClick = { navController.navigate(NavigationRoute.GALLERY) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Calculate
                MenuCard(
                    icon = R.drawable.icon_calculate,
                    title = stringResource(id = R.string.calculate),
                    onClick = { navController.navigate(NavigationRoute.CALCULATE) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                // Circuit & Schedule
                MenuCard(
                    icon = R.drawable.icon_schedule,
                    title = stringResource(id = R.string.circuit_schedule),
                    onClick = { navController.navigate(NavigationRoute.CIRCUIT) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

// Menu Navigasi
@Composable
fun MenuCard(
    icon: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color.White else LightF1Red)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview (showBackground = true)
@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}

