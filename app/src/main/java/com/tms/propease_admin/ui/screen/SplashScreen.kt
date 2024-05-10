package com.tms.propease_admin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.nav.AppNavigation
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import kotlinx.coroutines.delay

object SplashScreenDestination: AppNavigation {
    override val title: String = "Splash screen"
    override val route: String = "splash-screen"

}
@Composable
fun SplashScreenComposable(
    navigateToHomeScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
) {
    val viewModel: SplashScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var screenDelay by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        delay(2000L)
        screenDelay = false
    }

    if(!screenDelay) {
        if(uiState.executionStatus == ExecutionStatus.SUCCESS) {
            if(uiState.userDetails.userId != null) {
                navigateToHomeScreen()
            } else if(uiState.userDetails.userId == null) {
                navigateToRegistrationScreen()
            }
            viewModel.resetLoadingStatus()
        }
    }

    Box {
        SplashScreen(screenDelay = screenDelay)
    }
}

@Composable
fun SplashScreen(
    screenDelay: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.prop_ease_3),
            contentDescription = null
        )
        Text(
            text = "Admin",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        if(screenDelay) {
            CircularProgressIndicator()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    PropEaseAdminTheme {
        SplashScreen(
            screenDelay = true
        )
    }
}