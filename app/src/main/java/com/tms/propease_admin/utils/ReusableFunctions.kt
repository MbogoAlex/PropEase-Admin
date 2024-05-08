package com.tms.propease_admin.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

enum class ExecutionStatus {
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

