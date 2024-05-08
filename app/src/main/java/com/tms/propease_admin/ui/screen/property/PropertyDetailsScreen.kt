package com.tms.propease_admin.ui.screen.property

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.PropertyImages
import com.tms.propease_admin.utils.PropertyInfo

val property = PropertyDetails(
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
)
@Composable
fun PropertyDetailsScreenComposable() {
    Box {
        PropertyDetailsScreen(
            property = property,
            navigateToPreviousScreen = {}
        )
    }
}

@Composable
fun PropertyDetailsScreen(
    property: PropertyDetails,
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateToPreviousScreen() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }

        }
        PropertyImages(
            images = property.images
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        PropertyInfo(
            property = property,
            executionStatus = ExecutionStatus.INITIAL,
            onVerifyPropertyClicked = { /*TODO*/ })
    }
}





@Preview(showBackground = true)
@Composable
fun PropertyDetailsScreenPreview() {
    PropEaseAdminTheme {
        PropertyDetailsScreen(
            property = property,
            navigateToPreviousScreen = {}
        )
    }
}