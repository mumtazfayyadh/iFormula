package com.mumtazfayyadh0102.iformula.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumtazfayyadh0102.iformula.model.Gallery
import com.mumtazfayyadh0102.iformula.network.ApiStatus
import com.mumtazfayyadh0102.iformula.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Gallery>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = GalleryApi.service.getGallery()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("GalleryViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}