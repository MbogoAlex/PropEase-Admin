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

@Serializable
data class UnverifiedUsersResponseBody(
    val statusCode: Int,
    val message: String,
    val data: UsersProfile
)

@Serializable
data class UsersProfile(
    val profiles: List<UserVerificationProfile>
)

@Serializable
data class UserVerificationProfile(
    val documents: List<Document>,
    val user: UserProfile
)

@Serializable
data class Document(
    val id: Int,
    val name: String
)

@Serializable
data class UserVerificationResponseBody(
    val statusCode: Int,
    val message: String,
    val data: VerifiedUserProfile
)

@Serializable
data class VerifiedUserProfile(
    val profile: UserVerificationProfile
)

@Serializable
data class UnverifiedUserResponseBody(
    val statusCode: Int,
    val message: String,
    val data: UnverifiedUserProfile
)

@Serializable
data class UnverifiedUserProfile(
    val profiles: UserVerificationProfile
)

