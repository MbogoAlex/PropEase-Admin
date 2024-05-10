package com.tms.propease_admin.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequestBody(
    val fname: String,
    val lname: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)

@Serializable
data class UserRegistrationResponseBody(
    val statusCode: Int,
    val message: String,
    val data: UserAccountDt
)

@Serializable
data class UserAccountDt(
    val user: UserProfile
)

@Serializable
data class UserLoginRequestBody(
    val username: String,
    val password: String
)

@Serializable
data class UserLoginResponseBody(
    val statusCode: Int,
    val message: String,
    val data: LoggedInData
)

@Serializable
data class LoggedInData(
    val user: UserInfo,
)

@Serializable
data class UserInfo(
    val userInfo: UserProfile,
    val token: String
)