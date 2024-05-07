package com.tms.propease_admin.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

enum class ExecutionStatus {
    INITIAL,
    LOADING,
    SUCCESS,
    FAIL
}

fun checkIfEmailIsValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun InputForm(
    value: String,
    label: String,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions,
    leadingIcon: Int,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        shape = RoundedCornerShape(10.dp),
        value = value,
        label = {
            Text(text = label)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIcon), contentDescription = label)
        },
        keyboardOptions = keyboardOptions,
        isError = isError,
        onValueChange = onValueChange,
        modifier = modifier
    )
}