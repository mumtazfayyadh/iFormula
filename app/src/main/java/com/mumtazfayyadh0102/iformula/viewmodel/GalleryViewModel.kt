package com.mumtazfayyadh0102.iformula.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumtazfayyadh0102.iformula.model.Gallery
import com.mumtazfayyadh0102.iformula.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Gallery>())
        private set

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = GalleryApi.service.getGallery()
            } catch (e: Exception) {
                Log.d("GalleryViewModel", "Failure: ${e.message}")
            }
        }
    }
}