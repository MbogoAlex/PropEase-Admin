package com.tms.propease_admin.utils

import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.model.UserRole

val category = CategoryItem(
    id = 0,
    name = ""
)


val userProfile = UserProfile(
    id = 1,
    email = "",
    phoneNumber = "",
    imageUrl = null,
    approvalStatus = "pending",
    approved = false,
    roles = emptyList(),
    fname = "",
    lname = ""
)