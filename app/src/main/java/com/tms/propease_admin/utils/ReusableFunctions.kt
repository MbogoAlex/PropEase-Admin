package com.tms.propease_admin.utils

import androidx.compose.ui.graphics.Color
import java.text.NumberFormat
import java.util.Locale

enum class ExecutionStatus {
    INITIAL,
    LOADING,
    SUCCESS,
    FAIL
}

enum class VerificationStatus {
    INITIAL,
    LOADING,
    SUCCESS,
    FAIL
}

enum class EditStatus {
    INITIAL,
    LOADING,
    SUCCESS,
    FAIL
}
enum class Screen {
    VERIFIED_LIVE_PROPERTIES,
    VERIFIED_NOT_LIVE_PROPERTIES,
    UNVERIFIED_PROPERTIES,
    ARCHIVED_PROPERTIES,
    CATEGORIES,
    UNVERIFIED_USERS,
    PROFILE,
    NOTIFICATIONS,
    LOGOUT,
    LOGIN
}

data class PropertyScreenNavigationItem(
    val title: String,
    val icon: Int,
    val screen: Screen,
    val color: Color
)

data class UserScreenNavigationItem(
    val title: String,
    val icon: Int,
    val screen: Screen,
    val color: Color
)

data class ProfileScreenNavigationItem(
    val title: String,
    val icon: Int,
    val screen: Screen,
    val color: Color
)

fun checkIfEmailIsValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun formatMoneyValue(amount: Double): String {
    return  NumberFormat.getCurrencyInstance(Locale("en", "KE")).format(amount)
}

