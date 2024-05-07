package com.tms.propease_admin.accountManagement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme

@Composable
fun RegistrationScreenComposable() {
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
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    PropEaseAdminTheme {
        RegistrationScreen()
    }
}