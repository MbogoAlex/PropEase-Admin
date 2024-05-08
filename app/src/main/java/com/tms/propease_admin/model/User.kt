package com.tms.propease_admin.model

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseBody(
    val statusCode: Int,
    val message: String,
    val data: UsersDt
)

@Serializable
data class UsersDt(
    val profiles: List<UserProfile>
)

@Serializable
data class UserProfile(
    val id: Int,
    val email: String,
    val phoneNumber: String,
    val imageUrl: String?,
    val approvalStatus: String,
    val approved: Boolean,
    val roles: List<UserRole>,
    val fname: String,
    val lname: String
)

@Serializable
data class UserRole(
    val id: Int,
    val name: String,
)

@Serializable
data class UserResponseBody(
    val statusCode: Int,
    val message: String,
    val data: UserDt
)

@Serializable
data class UserDt(
    val profiles: UserProfile
)

