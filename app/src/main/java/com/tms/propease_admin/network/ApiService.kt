package com.tms.propease_admin.network

import com.tms.propease_admin.model.PropertiesResponseBody
import com.tms.propease_admin.model.PropertyResponseBody
import com.tms.propease_admin.model.PropertyVerificationRequestBody
import com.tms.propease_admin.model.PropertyVerificationResponseBody
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.model.UserLoginResponseBody
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.model.UserRegistrationResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("property/propertyId={propertyId}")
    suspend fun getPropertyDetails(
        @Header("Authorization") token: String,
        @Path("propertyId") propertyId: Int
    ): Response<PropertyResponseBody>

    @POST("property/propertyId={propertyId}/approve")
    suspend fun verifyProperty(
        @Header("Authorization") token: String,
        @Path("propertyId") propertyId: Int,
        @Body propertyVerificationRequestBody: PropertyVerificationRequestBody
    ): Response<PropertyVerificationResponseBody>

    @POST("property/propertyId={propertyId}/disapprove")
    suspend fun cancelPropertyVerification(
        @Header("Authorization") token: String,
        @Path("propertyId") propertyId: Int,
        @Body propertyVerificationRequestBody: PropertyVerificationRequestBody
    ): Response<PropertyVerificationResponseBody>
}