package com.tms.propease_admin.network

import com.tms.propease_admin.model.PropertiesResponseBody
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.model.UserLoginResponseBody
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.model.UserRegistrationResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register/admin")
    suspend fun registerUser(
        @Body user: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody>

    @POST("auth/login")
    suspend fun loginUser(
        @Body user: UserLoginRequestBody
    ): Response<UserLoginResponseBody>

    @GET("property/unapproved")
    suspend fun getUnverifiedProperties(
        @Header("Authorization") token: String
    ): Response<PropertiesResponseBody>
}