package com.mumtazfayyadh0102.iformula.network

import com.mumtazfayyadh0102.iformula.model.Gallery
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://gallery-app.sendiko.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GalleryApiService {
    @GET("photos")
    suspend fun getGallery(
        @Query("userId") userId: String = "null"
    ): List<Gallery>
}

object GalleryApi {
    val service: GalleryApiService by lazy {
        retrofit.create(GalleryApiService::class.java)
    }

    fun getGalleryUrl(fullUrl: String): String {
        return fullUrl
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }