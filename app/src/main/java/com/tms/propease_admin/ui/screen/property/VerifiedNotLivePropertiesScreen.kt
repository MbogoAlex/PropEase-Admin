package com.tms.propease_admin.ui.screen.property

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.PropertiesDisplay

val notLiveProperties = listOf<PropertyDetails>(
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = 2,
        price = 10000.0,
        approved = true,
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
        approved = true,
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
        approved = true,
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
        approved = true,
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
        approved = true,
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
        approved = true,
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
fun VerifiedNotLivePropertiesScreenComposable() {
    Box {
        VerifiedNotLivePropertiesScreen(
            properties = notLiveProperties
        )
    }
}

@Composable
fun VerifiedNotLivePropertiesScreen(
    properties: List<PropertyDetails>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PropertiesDisplay(
            properties = properties,
            navigateToSpecificProperty = {},
            executionStatus = ExecutionStatus.SUCCESS
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerifiedNotLivePropertiesScreenPreview() {
    PropEaseAdminTheme {
        VerifiedNotLivePropertiesScreen(
            properties = unapprovedProperties
        )
    }
}