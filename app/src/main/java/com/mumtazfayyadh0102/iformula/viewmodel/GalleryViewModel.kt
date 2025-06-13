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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class GalleryViewModel : ViewModel() {

    private val dataGallery = MutableStateFlow<List<Gallery>>(emptyList())
    val data: StateFlow<List<Gallery>> get() = dataGallery

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String = "null") {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                status.value = ApiStatus.LOADING
                val response = GalleryApi.service.getGallery(userId)
                dataGallery.value = response.photos
                status.value = ApiStatus.SUCCESS
                Log.d("GalleryViewModel", "retrieveData success, size: ${response.photos.size}")
            } catch (e: Exception) {
                errorMessage.value = "Failure: ${e.message}"
                status.value = ApiStatus.FAILED
                Log.e("GalleryViewModel", "retrieveData error: ${e.message}")
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
                    retrieveData()
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
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
                    retrieveData()
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun deleteItem(id: Int, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.deleteGallery(id)
                if (result.status == "success") {
                    retrieveData()
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun clearMessage() {
        errorMessage.value = null
    }
}

