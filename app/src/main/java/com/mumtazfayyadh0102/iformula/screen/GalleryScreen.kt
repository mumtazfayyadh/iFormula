package com.mumtazfayyadh0102.iformula.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.GalleryImage
import com.mumtazfayyadh0102.iformula.navigation.NavigationRoute
import com.mumtazfayyadh0102.iformula.ui.theme.DarkF1Black
import com.mumtazfayyadh0102.iformula.ui.theme.LightF1Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(navController: NavController) {
    val appBarColor = if (isSystemInDarkTheme()) DarkF1Black else LightF1Red

    val galleryImages = listOf(
        GalleryImage(
            R.drawable.gallery1,
            "Legend Mclaren"
        ),
        GalleryImage(
            R.drawable.gallery2,
            "Start Grid"
        ),
        GalleryImage(
            R.drawable.gallery3,
            "Ferrari"
        ),
        GalleryImage(
            R.drawable.gallery4,
            "Racing"
        ),
        GalleryImage(
            R.drawable.gallery5,
            "Wet Track"
        ),
        GalleryImage(
            R.drawable.gallery6,
            "Driver"
        )
    )

    var currentImageIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.gallery),
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
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
            Text(
                text = stringResource(id = R.string.gallery),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = galleryImages[currentImageIndex].imageRes),
                    contentDescription = galleryImages[currentImageIndex].description,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Deskripsi gambar
            Text(
                text = galleryImages[currentImageIndex].description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol navigasi
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol Previous
                Button(
                    onClick = {
                        if (currentImageIndex > 0) {
                            currentImageIndex--
                        } else {
                            // Kembali ke gambar terakhir jika di gambar pertama
                            currentImageIndex = galleryImages.size - 1
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.previous),
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Tombol Next
                Button(
                    onClick = {
                        if (currentImageIndex < galleryImages.size - 1) {
                            currentImageIndex++
                        } else {
                            // Kembali ke gambar pertama
                            currentImageIndex = 0
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(id = R.string.next),
                        color = Color.White,
                        )
                }
            }
        }
    }
}