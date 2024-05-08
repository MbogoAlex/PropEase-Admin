package com.tms.propease_admin.network

import com.tms.propease_admin.model.PropertiesResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("property/unapproved")
    suspend fun getUnverifiedProperties(
        @Header("Authorization") token: String
    ): Response<PropertiesResponseBody>
}