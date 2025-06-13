package com.mumtazfayyadh0102.iformula.network

import com.mumtazfayyadh0102.iformula.model.Gallery
import com.mumtazfayyadh0102.iformula.model.GalleryResponse
import com.mumtazfayyadh0102.iformula.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
        @Query("userId") userId: String = "null",
    ): GalleryResponse

    @Multipart
    @POST("photos")
    suspend fun postGallery(
        @Part("userId") userId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("photos/{id}")
    suspend fun deleteGallery(
        @Path("id") id: Int
    ): OpStatus
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