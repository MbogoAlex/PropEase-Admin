package com.tms.propease_admin.ui.screen.accountManagement

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.nav.AppNavigation
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.InputForm
import com.tms.propease_admin.utils.checkIfEmailIsValid

object RegistrationScreenDestination: AppNavigation {
    override val title: String = "Registration screen"
    override val route: String = "registration-screen"

}

@Composable
fun RegistrationScreenComposable(
    navigateToLoginScreenWithArgs: (phoneNumber: String, password: String) -> Unit,
    navigateToLoginScreenWithoutArgs: () -> Unit,
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler(onBack = {
        activity?.finish()
    })
    val context = LocalContext.current
    val viewModel: RegistrationScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.executionStatus == ExecutionStatus.SUCCESS) {
        navigateToLoginScreenWithArgs(uiState.phone, uiState.password)
        viewModel.resetRegistrationStatus()
    } else if(uiState.executionStatus == ExecutionStatus.FAIL) {
        Toast.makeText(context, uiState.registrationFailureMessage, Toast.LENGTH_SHORT).show()
    }

    Box {
        RegistrationScreen(
            fname = uiState.fname,
            lname = uiState.lname,
            email = uiState.email,
            password = uiState.password,
            phoneNumber = uiState.phone,
            registerUserButtonEnabled = uiState.buttonEnabled,
            exitApp = {
                activity?.finish()
            },
            onfNameChange = {
                viewModel.updatefname(it)
                viewModel.checkIfFieldValuesValid()
            },
            onlNameChange = {
                viewModel.updatelname(it)
                viewModel.checkIfFieldValuesValid()
            },
            onEmailChange = {
                viewModel.updateEmail(it)
                viewModel.checkIfFieldValuesValid()
            },
            onPhoneNumberChange = {
                viewModel.updatePhone(it)
                viewModel.checkIfFieldValuesValid()
            },
            onPasswordChange = {
                viewModel.updatePassword(it)
                viewModel.checkIfFieldValuesValid()
            },
            onRegisterUser = {
                viewModel.registerUser()
            },
            executionStatus = uiState.executionStatus,
            navigateToLoginScreenWithoutArgs = navigateToLoginScreenWithoutArgs
        )
    }
}

@Composable
fun RegistrationScreen(
    fname: String,
    lname: String,
    email: String,
    password: String,
    phoneNumber: String,
    registerUserButtonEnabled: Boolean,
    exitApp: () -> Unit,
    onfNameChange: (newValue: String) -> Unit,
    onlNameChange: (newValue: String) -> Unit,
    onEmailChange: (newValue: String) -> Unit,
    onPhoneNumberChange: (newValue: String) -> Unit,
    onPasswordChange: (newValue: String) -> Unit,
    onRegisterUser: () -> Unit,
    executionStatus: ExecutionStatus,
    navigateToLoginScreenWithoutArgs: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        IconButton(onClick = exitApp) {
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
                    value = fname,
                    label = "first name",
                    onValueChange = onfNameChange,
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
                    value = lname,
                    label = "last name",
                    onValueChange = onlNameChange,
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
                value = phoneNumber,
                label = "phone number",
                onValueChange = onPhoneNumberChange,
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
                value = email,
                label = "email",
                onValueChange = onEmailChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = R.drawable.email,
                isError = !checkIfEmailIsValid(email),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputForm(
                value = password,
                label = "password",
                onValueChange = onPasswordChange,
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
                enabled = registerUserButtonEnabled && executionStatus != ExecutionStatus.LOADING,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onRegisterUser
            ) {
                if(executionStatus == ExecutionStatus.LOADING) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Register")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = navigateToLoginScreenWithoutArgs
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
        RegistrationScreen(
            fname = "",
            lname = "",
            email = "",
            password = "",
            phoneNumber = "",
            registerUserButtonEnabled = false,
            exitApp = { /*TODO*/ },
            onfNameChange = {},
            onlNameChange = {},
            onEmailChange = {},
            onPhoneNumberChange = {},
            onPasswordChange = {},
            onRegisterUser = { /*TODO*/ },
            executionStatus = ExecutionStatus.INITIAL,
            navigateToLoginScreenWithoutArgs = {}
        )
    }
}