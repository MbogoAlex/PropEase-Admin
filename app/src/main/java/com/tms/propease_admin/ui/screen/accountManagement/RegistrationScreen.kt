package com.tms.propease_admin.ui.screen.accountManagement

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tms.propease_admin.R
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.InputForm
import com.tms.propease_admin.utils.checkIfEmailIsValid

@Composable
fun RegistrationScreenComposable(
    navigateToLoginScreenWithArgs: (phoneNumber: String, password: String) -> Unit,
    navigateToLoginScreenWithoutArgs: () -> Unit,
    exitApp: () -> Unit,
) {
    BackHandler(onBack = exitApp)
    Box {
        RegistrationScreen()
    }
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Exit app"
            )
        }
        Text(
            text = "Admin Account",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputForm(
                    value = "",
                    label = "first name",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    leadingIcon = R.drawable.person,
                    isError = false,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(20.dp))
                InputForm(
                    value = "",
                    label = "last name",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    leadingIcon = R.drawable.person,
                    isError = false,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            InputForm(
                value = "",
                label = "phone number",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
                leadingIcon = R.drawable.phone,
                isError = false,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputForm(
                value = "",
                label = "email",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = R.drawable.email,
                isError = !checkIfEmailIsValid(""),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputForm(
                value = "",
                label = "password",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                leadingIcon = R.drawable.password,
                isError = false,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Sign in")
            }
        }

    }   
}

@Preview(showBackground = true)
@Composable
fun InputFormPreview() {
    PropEaseAdminTheme {
        InputForm(
            value = "",
            label = "first name",
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            leadingIcon = R.drawable.person,
            isError = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    PropEaseAdminTheme {
        RegistrationScreen()
    }
}