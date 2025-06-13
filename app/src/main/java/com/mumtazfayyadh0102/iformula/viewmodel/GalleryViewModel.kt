package com.mumtazfayyadh0102.iformula.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumtazfayyadh0102.iformula.model.Gallery
import com.mumtazfayyadh0102.iformula.network.ApiStatus
import com.mumtazfayyadh0102.iformula.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class GalleryViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Gallery>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String = "null") {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                val response = GalleryApi.service.getGallery(userId)
                data.value = response.photos
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                errorMessage.value = "Failure: ${e.message}"
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, title: String, description: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.postGallery(
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }

            } catch (e: Exception) {
                Log.e("GalleryViewModel", " Exception: ${e.message}")
                errorMessage.value = "${e.message}"
            }
        }
    }

    fun updateData(id: Int, userId: String, title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.updateGallery(
                    id,
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull())
                )
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "Exception: ${e.message}")
                errorMessage.value = "${e.message}"
            }
        }
    }

    fun deleteItem(id: Int, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.deleteGallery(id)
                if (result.status == "success") {
                    delay(300)
                    retrieveData(userId)
                } else {
                    Log.e("GalleryViewModel", "$id: ${result.message}")
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                val message = e.message ?: "Unknown error"
                Log.e("GalleryViewModel", "Exception: $message")
                errorMessage.value = "${e.message}"
            }
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.deleteGallery(id)
                if (result.status == "success") {
                    retrieveData()
                } else {
                    Log.e("GalleryViewModel", "Failure: ${result.message}")
                }
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "Exception: ${e.message}")
                errorMessage.value = "${e.message}"
            }
        }
    }


    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpeg".toMediaTypeOrNull(), 0, byteArray.size
        )
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody
        )
    }

    fun clearMessage() {
        errorMessage.value = null
    }
}
