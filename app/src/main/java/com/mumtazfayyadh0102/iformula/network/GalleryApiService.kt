package com.mumtazfayyadh0102.iformula.network

import com.mumtazfayyadh0102.iformula.model.Gallery
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "indraazimi/mobpro1-compose/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GalleryApiService {
    @GET("static-api.json")
    suspend fun getGallery(): List<Gallery>
}

object GalleryApi {
    val service: GalleryApiService by lazy {
        retrofit.create(GalleryApiService::class.java)
    }

    fun getGalleryUrl(imageId: String): String {
        return "$BASE_URL$imageId.jpg"
    }
}