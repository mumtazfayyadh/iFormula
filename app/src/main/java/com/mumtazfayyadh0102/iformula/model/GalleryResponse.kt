package com.mumtazfayyadh0102.iformula.model

data class GalleryResponse(
    val status: Int,
    val message: String,
    val photos: List<Gallery>
)
