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
            Log.d("GalleryViewModel", "📤 Mengambil data gallery untuk userId: $userId")
            try {
                val response = GalleryApi.service.getGallery(userId)
                data.value = response.photos
                status.value = ApiStatus.SUCCESS
                Log.d("GalleryViewModel", "✅ Jumlah data diterima: ${response.photos.size}")
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "❌ Gagal ambil data: ${e.message}")
                errorMessage.value = "Gagal mengambil data: ${e.message}"
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, title: String, description: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("GalleryViewModel", "📤 Mengirim data: userId=$userId, title=$title")
                val result = GalleryApi.service.postGallery(
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )

                if (result.status == "success") {
                    Log.d("GalleryViewModel", "Data berhasil disimpan, refresh data")
                    retrieveData(userId)
                } else {
                    Log.e("GalleryViewModel", " Gagal simpan data: ${result.message}")
                    throw Exception(result.message)
                }

            } catch (e: Exception) {
                Log.e("GalleryViewModel", " Exception saat simpan data: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun updateData(id: Int, userId: String, title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("GalleryViewModel", "📤 Mengupdate data: id=$id, userId=$userId")
                val result = GalleryApi.service.updateGallery(
                    id,
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    title.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (result.status == "success") {
                    Log.d("GalleryViewModel", "✅ Berhasil update data")
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "❌ Gagal update: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }



    fun deleteItem(id: Int, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.deleteGallery(id)
                if (result.status == "success") {
                    Log.d("GalleryViewModel", "✅ Item $id berhasil dihapus")
                    delay(300)
                    retrieveData(userId)
                } else {
                    Log.e("GalleryViewModel", "❌ Gagal hapus item $id: ${result.message}")
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                val message = e.message ?: "Unknown error"
                Log.e("GalleryViewModel", "❌ Exception delete: $message")
                errorMessage.value = "Gagal hapus data: $message"
            }
        }
    }

    fun deleteData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = GalleryApi.service.deleteGallery(id)
                if (result.status == "success") {
                    retrieveData() // atau dengan userId kalau perlu
                } else {
                    Log.e("GalleryViewModel", "Gagal hapus: ${result.message}")
                }
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "Exception hapus data: ${e.message}")
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
