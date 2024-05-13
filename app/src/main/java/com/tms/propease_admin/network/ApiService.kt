package com.tms.propease_admin.network

import com.tms.propease_admin.model.CategoryResponseBody
import com.tms.propease_admin.model.FilteredPropertiesResponseBody
import com.tms.propease_admin.model.NewCategoryRequestBody
import com.tms.propease_admin.model.NewCategoryResponseBody
import com.tms.propease_admin.model.PropertiesResponseBody
import com.tms.propease_admin.model.PropertyResponseBody
import com.tms.propease_admin.model.PropertyVerificationRequestBody
import com.tms.propease_admin.model.PropertyVerificationResponseBody
import com.tms.propease_admin.model.UnverifiedUserResponseBody
import com.tms.propease_admin.model.UnverifiedUsersResponseBody
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.model.UserLoginResponseBody
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.model.UserRegistrationResponseBody
import com.tms.propease_admin.model.UserResponseBody
import com.tms.propease_admin.model.UserVerificationResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("property/unpaid")
    suspend fun getUnpaidProperties(
        @Header("Authorization") token: String,
    ): Response<PropertiesResponseBody>

    @GET("profile/unverified")
    suspend fun getUnverifiedUsers(
        @Header("Authorization") token: String,
    ): Response<UnverifiedUsersResponseBody>

    @GET("category")
    suspend fun getCategories(
//        @Header("Authorization") token: String,
    ): Response<CategoryResponseBody>

    @GET("property/filter")
    suspend fun getFilteredLiveProperties(
//        @Header("Authorization") token: String,
        @Query("location") location: String,
        @Query("rooms") rooms: String,
        @Query("categoryId") categoryId: String
    ): Response<FilteredPropertiesResponseBody>

    @POST("profile/userId={userId}/verify")
    suspend fun verifyUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Response<UserVerificationResponseBody>

    @GET("profile/userId={userId}/unverifiedUser")
    suspend fun getUnverifiedUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Response<UnverifiedUserResponseBody>

    @POST("category/create")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body categoryRequestBody: NewCategoryRequestBody
    ): Response<NewCategoryResponseBody>

    @GET("profile/userId={userId}/user")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Response<UserResponseBody>
}