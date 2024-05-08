package com.tms.propease_admin.ui.screen.accountManagement

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

object LoginScreenDestination: AppNavigation{
    override val title: String = "Login screen"
    override val route: String = "login-screen"
    val phoneNumber: String = "phoneNumber"
    val password: String = "password"
    val routeWithArgs: String = "$route/{$phoneNumber}/{$password}"
}
@Composable
fun LoginScreenComposable(
    navigateToHomeScreenWithoutArgs: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
){
    val context = LocalContext.current

    val viewModel: LoginScreenViewModel = viewModel(factory =  AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.executionStatus == ExecutionStatus.SUCCESS) {
        navigateToHomeScreenWithoutArgs()
        viewModel.resetLoginStatus()
    } else if(uiState.executionStatus == ExecutionStatus.FAIL) {
        Toast.makeText(context, uiState.loginFailureMessage, Toast.LENGTH_SHORT).show()
    }

    Box {
        LoginScreen(
            phone = uiState.phone,
            password = uiState.password,
            loginButtonEnabled = uiState.buttonEnabled,
            executionStatus = uiState.executionStatus,
            onPhoneNumberChanged = {
                viewModel.updatePhoneNumber(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            onPasswordChanged = {
                viewModel.updatePassword(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            onLoginButtonClicked = {
                viewModel.loginUser()
            },
            navigateToPreviousScreen = navigateToPreviousScreen,
            navigateToRegistrationScreen = navigateToRegistrationScreen
        )
    }
}
@Composable
fun LoginScreen(
    phone: String,
    password: String,
    loginButtonEnabled: Boolean,
    executionStatus: ExecutionStatus,
    onPhoneNumberChanged: (newValue: String) -> Unit,
    onPasswordChanged: (newValue: String) -> Unit,
    onLoginButtonClicked: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        IconButton(onClick = navigateToPreviousScreen) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous screen"
            )
        }
        Image(
            painter = painterResource(id = R.drawable.prop_ease_3),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Admin login",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputForm(
            value = phone,
            label = "phone number",
            onValueChange = onPhoneNumberChanged,
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
            value = password,
            label = "password",
            onValueChange = onPasswordChanged,
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
            enabled = loginButtonEnabled && executionStatus != ExecutionStatus.LOADING,
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onLoginButtonClicked,
        ) {
            if(executionStatus == ExecutionStatus.LOADING) {
                CircularProgressIndicator()
            } else {
                Text(text = "Sign in")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = navigateToRegistrationScreen
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PropEaseAdminTheme {
        LoginScreen(
            phone = "",
            password = "",
            loginButtonEnabled = false,
            executionStatus = ExecutionStatus.INITIAL,
            onPhoneNumberChanged = {},
            onPasswordChanged = {},
            onLoginButtonClicked = { /*TODO*/ },
            navigateToPreviousScreen = { /*TODO*/ },
            navigateToRegistrationScreen = { /*TODO*/ })
    }
}