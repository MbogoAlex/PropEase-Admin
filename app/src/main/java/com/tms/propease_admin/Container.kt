package com.tms.propease_admin

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tms.propease_admin.network.ApiRepository
import com.tms.propease_admin.network.ApiService
import com.tms.propease_admin.network.NetworkRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface Container {
    val apiRepository: ApiRepository
}

class AppContainer(private val context: Context): Container {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val baseUrl = "http://172.105.90.112:8080/pManager/api/"
//    private val baseUrl = "http://192.168.80.6:8080/pManager/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val apiRepository: NetworkRepository by lazy {
        NetworkRepository(retrofitService)
    }

}