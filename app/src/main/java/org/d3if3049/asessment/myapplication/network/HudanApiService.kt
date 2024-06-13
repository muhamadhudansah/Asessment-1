package org.d3if3049.asessment.myapplication.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3049.asessment.myapplication.model.Hudan
import org.d3if3049.asessment.myapplication.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://api.hudansah.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface HudanApiService {
    @GET("json.php")
    suspend fun getHudan(
        @Query("auth") userId: String
    ): List<Hudan>

    @Multipart
    @POST("json.php")
    suspend fun postHudan(
        @Part("auth") userId: String,
        @Part("judul") judul: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("json.php")
    suspend fun deleteHudan(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object HudanApi {
    val service: HudanApiService by lazy {
        retrofit.create(HudanApiService::class.java)
    }
    fun getHudanUrl(imageId: String): String {
        return "${BASE_URL}$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }