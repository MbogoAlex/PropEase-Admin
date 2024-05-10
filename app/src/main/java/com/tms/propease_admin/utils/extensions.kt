package com.tms.propease_admin.utils

import com.tms.propease_admin.appDataStore.DSUserModel

data class UserDetails(
    val userId: Int? = 0,
    val userName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val token: String = "",
    val approvalStatus: String = ""
)

fun DSUserModel.toUserData() : UserDetails = UserDetails(
    userId = userId,
    userName = userName,
    phoneNumber = phoneNumber,
    email = email,
    password = password,
    token = token,
    approvalStatus = approvalStatus
)