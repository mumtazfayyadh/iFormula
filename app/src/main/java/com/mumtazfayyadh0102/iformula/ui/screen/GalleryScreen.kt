package com.mumtazfayyadh0102.iformula.ui.screen

import android.content.ContentResolver
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.mumtazfayyadh0102.iformula.R
import com.mumtazfayyadh0102.iformula.model.Gallery
import com.mumtazfayyadh0102.iformula.model.User
import com.mumtazfayyadh0102.iformula.navigation.Screen
import com.mumtazfayyadh0102.iformula.network.ApiStatus
import com.mumtazfayyadh0102.iformula.network.UserDataStore
import com.mumtazfayyadh0102.iformula.util.signIn
import com.mumtazfayyadh0102.iformula.util.signOut
import com.mumtazfayyadh0102.iformula.viewmodel.GalleryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(navController: NavController) {
    val appBarColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current

    val dataStore = UserDataStore.getInstance(context)

    val user by dataStore.userFlow.collectAsState(initial = User("", "", ""))

    val viewModel: GalleryViewModel = viewModel()
    val errorMessage by viewModel.errorMessage

    var showDialog by remember { mutableStateOf(false) }
    var showGalleryDialog by remember { mutableStateOf(false) }

    var showEditDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<Gallery?>(null) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Gallery?>(null) }

    var showDetailDialog by remember { mutableStateOf(false) }
    var selectedGallery by remember { mutableStateOf<Gallery?>(null) }

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    var launcher = rememberLauncherForActivityResult(CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
        if (bitmap != null) showGalleryDialog = true
    }

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
        },
        floatingActionButton = {
            if (user.email.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    val options = CropImageContractOptions(
                        null, CropImageOptions(
                            imageSourceIncludeGallery = true,
                            imageSourceIncludeCamera = true,
                            fixAspectRatio = true
                        )
                    )
                    launcher.launch(options)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_image)
                    )
                }
            }
        }
    ) { innerPadding ->
        ScreenContent(
            viewModel,
            user.email,
            modifier = Modifier.padding(innerPadding).padding(4.dp),
            onEdit = { gallery ->
                itemToEdit = gallery
                showEditDialog = true
            },
            onDetail = { gallery ->
                selectedGallery = gallery
                showDetailDialog = true
            }
        )
        if (showDialog) {
            ProfileDialog(
                user = user,
                onDismissRequest = { showDialog = false }) {
                CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore) }
                showDialog = false
            }
        }

        if (showGalleryDialog) {
            GalleryDialog(
                bitmap = bitmap,
                onDismissRequest = { showGalleryDialog = false }) { title, description ->
                viewModel.saveData(user.email, title, description, bitmap!!)
                showGalleryDialog = false
            }
        }

        if (showDetailDialog && selectedGallery != null) {
            DetailDialog(
                imageUrl = selectedGallery!!.imageUrl,
                title = selectedGallery!!.title,
                description = selectedGallery!!.description,
                onDismissRequest = { showDetailDialog = false }
            )
        }

        if (showEditDialog && itemToEdit != null) {
            EditDialog(
                imageUrl = itemToEdit!!.imageUrl,
                titleInit = itemToEdit!!.title,
                descriptionInit = itemToEdit!!.description,
                onDismissRequest = { showEditDialog = false },
                onUpdate = { newTitle, newDesc ->
                    viewModel.updateData(itemToEdit!!.id, user.email, newTitle, newDesc)
                    showEditDialog = false
                }
            )
        }

        if (showDeleteDialog && itemToDelete != null) {
            DeleteDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    itemToDelete = null
                },
                onConfirmation = {
                    viewModel.deleteItem(itemToDelete!!.id, user.email)
                    showDeleteDialog = false
                    itemToDelete = null
                }
            )
        }

        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }
}

@Composable
fun ScreenContent(
    viewModel: GalleryViewModel,
    userId: String,
    modifier: Modifier = Modifier,
    onEdit: (Gallery) -> Unit,
    onDetail: (Gallery) -> Unit
) {
    val data by viewModel.data
    val status by viewModel.status.collectAsState()

    var selectedGallery by remember { mutableStateOf<Gallery?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Gallery?>(null) }

    LaunchedEffect(userId) {
        viewModel.retrieveData(userId)
    }

    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ApiStatus.SUCCESS -> {
            LazyVerticalGrid(
                modifier = modifier.fillMaxSize().padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) { gallery ->
                    ListItem(
                        gallery = gallery,
                        onDeleteClick = {
                            itemToDelete = gallery
                            showDeleteDialog = true
                        },
                        onClick = { onDetail(gallery) },
                        onEditClick = { onEdit(gallery) },
                        showDelete = userId.isNotEmpty() && userId != "null",
                        showEdit = userId.isNotEmpty() && userId != "null"
                    )
                }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.error_image))
                Button(
                    onClick = { viewModel.retrieveData(userId) },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }
        }
    }

    if (showDeleteDialog && itemToDelete != null) {
        DeleteDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmation = {
                viewModel.deleteData(itemToDelete!!.id)
                showDeleteDialog = false
            }
        )
    }
}


@Composable
fun ListItem(
    gallery: Gallery,
    onEditClick: () -> Unit,
    showEdit: Boolean = true,
    onDeleteClick: () -> Unit,
    showDelete: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(4.dp).clickable { onClick() }.border(1.dp, Color.Gray),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(gallery.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.gallery_image, gallery.title),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.baseline_broken_image_24),
            modifier = Modifier.fillMaxWidth().aspectRatio(1f).padding(4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0f, 0f, 0f, 0.5f))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = gallery.title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            if (showEdit) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                }
            }
            if (showDelete) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_confirm),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

private fun getCroppedImage(
    resolver: ContentResolver,
    result: CropImageView.CropResult
): Bitmap? {
    if (!result.isSuccessful) {
        Log.e("IMAGE", "Error: ${result.error}")
        return null
    }

    val uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val source = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GalleryScreenPreview() {
    GalleryScreen(navController = NavController(LocalContext.current))
}