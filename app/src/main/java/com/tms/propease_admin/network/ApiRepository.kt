package com.tms.propease_admin.network

import com.tms.propease_admin.model.CategoryResponseBody
import com.tms.propease_admin.model.FilteredPropertiesResponseBody
import com.tms.propease_admin.model.PropertiesResponseBody
import com.tms.propease_admin.model.PropertyResponseBody
import com.tms.propease_admin.model.PropertyVerificationRequestBody
import com.tms.propease_admin.model.PropertyVerificationResponseBody
import com.tms.propease_admin.model.UnverifiedUsersResponseBody
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.model.UserLoginResponseBody
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.model.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(user: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun loginUser(user: UserLoginRequestBody): Response<UserLoginResponseBody>
    suspend fun getUnverifiedProperties(token: String): Response<PropertiesResponseBody>

    suspend fun getPropertyDetails(token: String, propertyId: Int): Response<PropertyResponseBody>

    suspend fun verifyProperty(token: String, propertyId: Int, propertyVerificationRequestBody: PropertyVerificationRequestBody): Response<PropertyVerificationResponseBody>

    suspend fun cancelPropertyVerification(token: String, propertyId: Int, propertyVerificationRequestBody: PropertyVerificationRequestBody): Response<PropertyVerificationResponseBody>
    suspend fun getUnpaidProperties(token: String): Response<PropertiesResponseBody>

    suspend fun getUnverifiedUsers(token: String): Response<UnverifiedUsersResponseBody>

    suspend fun getCategories(): Response<CategoryResponseBody>

    suspend fun getFilteredLiveProperties(location: String, rooms: String, categoryId: String): Response<FilteredPropertiesResponseBody>
}

class NetworkRepository(private val apiService: ApiService): ApiRepository {
    override suspend fun registerUser(user: UserRegistrationRequestBody): Response<UserRegistrationResponseBody> = apiService.registerUser(
        user = user
    )

    override suspend fun loginUser(user: UserLoginRequestBody): Response<UserLoginResponseBody> = apiService.loginUser(
        user = user
    )

    override suspend fun getUnverifiedProperties(token: String): Response<PropertiesResponseBody> = apiService.getUnverifiedProperties(
        token = "Bearer $token"
    )

    override suspend fun getPropertyDetails(
        token: String,
        propertyId: Int
    ): Response<PropertyResponseBody> = apiService.getPropertyDetails(
        token = "Bearer $token",
        propertyId = propertyId
    )

    override suspend fun verifyProperty(
        token: String,
        propertyId: Int,
        propertyVerificationRequestBody: PropertyVerificationRequestBody
    ): Response<PropertyVerificationResponseBody> = apiService.verifyProperty(
        token = "Bearer $token",
        propertyId = propertyId,
        propertyVerificationRequestBody = propertyVerificationRequestBody
    )

    override suspend fun cancelPropertyVerification(
        token: String,
        propertyId: Int,
        propertyVerificationRequestBody: PropertyVerificationRequestBody
    ): Response<PropertyVerificationResponseBody> = apiService.cancelPropertyVerification(
        token = "Bearer $token",
        propertyId = propertyId,
        propertyVerificationRequestBody = propertyVerificationRequestBody
    )

    override suspend fun getUnpaidProperties(token: String): Response<PropertiesResponseBody> = apiService.getUnpaidProperties(
        token = "Bearer $token"
    )

    override suspend fun getUnverifiedUsers(token: String): Response<UnverifiedUsersResponseBody> = apiService.getUnverifiedUsers(
        token = "Bearer $token"
    )

    override suspend fun getCategories(): Response<CategoryResponseBody> = apiService.getCategories()

    override suspend fun getFilteredLiveProperties(
        location: String,
        rooms: String,
        categoryId: String
    ): Response<FilteredPropertiesResponseBody> = apiService.getFilteredLiveProperties(
        location = location,
        rooms = rooms,
        categoryId = categoryId
    )

}