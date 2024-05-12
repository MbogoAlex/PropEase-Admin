package com.tms.propease_admin.ui.screen.property

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.PropertiesDisplay

val unapprovedProperties = listOf<PropertyDetails>(
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = false,
        paid = false,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
)

@Composable
fun UnverifiedPropertiesScreenComposable(
    navigateToHomeScreenWithoutArgs: () -> Unit,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
){
    val viewModel: UnverifiedPropertiesScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    BackHandler(onBack = navigateToHomeScreenWithoutArgs)

    Box {
        UnverifiedPropertiesScreen(
            properties = uiState.properties,
            navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen
        )
    }
}

@Composable
fun UnverifiedPropertiesScreen(
    properties: List<PropertyDetails>,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Unverified properties",
            fontWeight = FontWeight.Bold
        )
        PropertiesDisplay(
            properties = properties,
            navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen,
            executionStatus = ExecutionStatus.SUCCESS
        )
    }

}

@Preview(showBackground = true)
@Composable
fun UnverifiedPropertiesScreenPreview() {
    PropEaseAdminTheme {
        UnverifiedPropertiesScreen(
            properties = unapprovedProperties,
            navigateToSpecificPropertyScreen = {}
        )
    }
}