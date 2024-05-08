package com.tms.propease_admin.network

import com.tms.propease_admin.model.PropertiesResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun getUnverifiedProperties(token: String): Response<PropertiesResponseBody>
}

class NetworkRepository(private val apiService: ApiService): ApiRepository {
    override suspend fun getUnverifiedProperties(token: String): Response<PropertiesResponseBody> = apiService.getUnverifiedProperties(
        token = "Bearer $token"
    )

}