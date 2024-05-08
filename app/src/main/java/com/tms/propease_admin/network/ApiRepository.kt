package com.tms.propease_admin.network

import com.tms.propease_admin.model.PropertiesResponseBody
import com.tms.propease_admin.model.UserLoginRequestBody
import com.tms.propease_admin.model.UserLoginResponseBody
import com.tms.propease_admin.model.UserRegistrationRequestBody
import com.tms.propease_admin.model.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(user: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun loginUser(user: UserLoginRequestBody): Response<UserLoginResponseBody>
    suspend fun getUnverifiedProperties(token: String): Response<PropertiesResponseBody>
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

}