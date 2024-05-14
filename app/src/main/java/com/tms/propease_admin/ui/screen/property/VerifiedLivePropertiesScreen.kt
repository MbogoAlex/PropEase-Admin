package com.tms.propease_admin.ui.screen.property

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.model.CategoryItem
import com.tms.propease_admin.model.PaymentDetails
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.model.PropertyImage
import com.tms.propease_admin.model.PropertyLocation
import com.tms.propease_admin.model.PropertyOwner
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import com.tms.propease_admin.utils.PropertiesDisplay
import com.tms.propease_admin.utils.PropertiesFilterSection

val propertyOwner = PropertyOwner(
    userId = 1,
    email = "abc@gmail.com",
    phoneNumber = "12345678",
    profilePic = null,
    approved = true,
    fname = "Alex",
    lname = "Mbogo"
)

val propertyLocation = PropertyLocation(
    address = "Nairobi town",
    county = "Nairobi",
    latitude = 0.0,
    longitude = 0.0
)

val propertyPayment = PaymentDetails(
    id = 1,
    partnerReferenceID = "1234321",
    transactionID = "123432",
    msisdn = "123432",
    partnerTransactionID = "1234321",
    payerTransactionID = "1234321",
    receiptNumber = "1234321",
    transactionAmount = 200.0,
    paymentComplete = true,
    createdAt = 2345432222,
    updatedAt = 1322222233,
    transactionStatus = "PAID"
)

val images = listOf<PropertyImage>(
    PropertyImage(
        id = 1,
        name = "",
        approved = true
    )
)

val properties = listOf<PropertyDetails>(
    PropertyDetails(
        user = propertyOwner,
        propertyId = 1,
        title = "A new property",
        description = "This is a new property",
        category = "Rental",
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
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
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
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
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
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
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
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
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
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
        rooms = "",
        price = 10000.0,
        approved = true,
        paid = true,
        postedDate = "2024-03-03 12:44",
        deletionTime = "2024-03-03 12:44",
        features = listOf("Balcony", "Wi-fi", "Water"),
        location = propertyLocation,
        paymentDetails =propertyPayment,
        images = images
    ),
)

@Composable
fun VerifiedLivePropertiesScreenComposable(
    navigateToLoginScreenWithArgs: (phoneNumber: String, password: String) -> Unit,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)

    BackHandler(onBack = {
        activity?.finish()
    })

    val viewModel: VerifiedLivePropertiesScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.loadPropertiesStatusCode == 401) {
        navigateToLoginScreenWithArgs(
            uiState.userDetails.phoneNumber,
            uiState.userDetails.password
        )
        viewModel.changeStatusCode()
    }

    Box {
        VerifiedLivePropertiesScreen(
            rooms = uiState.rooms,
            properties = uiState.properties,
            filteringOn = uiState.filteringOn,
            selectedLocation = uiState.locationSelected,
            categories = uiState.categories,
            categoryNameSelected = uiState.categoryNameSelected,
            numberOfRoomsSelected = uiState.roomsSelected,
            onSearchLocationChanged = {
                viewModel.filterByLocation(it)
            },
            onChangeNumberOfRooms = {
                viewModel.filterByNumOfRooms(it)
            },
            onChangeCategory = {
                viewModel.filterByCategory(it)
            },
            undoFilter = { viewModel.undoFiltering() },
            navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen
        )
    }
}
@Composable
fun VerifiedLivePropertiesScreen(
    rooms: List<String>,
    properties: List<PropertyDetails>,
    filteringOn: Boolean,
    selectedLocation: String,
    categories: List<CategoryItem>,
    categoryNameSelected: String,
    numberOfRoomsSelected: String,
    onSearchLocationChanged: (location: String) -> Unit,
    onChangeNumberOfRooms: (rooms: String) -> Unit,
    onChangeCategory: (categoryItem: CategoryItem) -> Unit,
    undoFilter: () -> Unit,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            PropertiesFilterSection(
                rooms = rooms,
                filteringOn = filteringOn,
                selectedLocation = selectedLocation,
                categories = categories,
                categoryNameSelected = categoryNameSelected,
                numberOfRoomsSelected = numberOfRoomsSelected,
                onSearchLocationChanged = onSearchLocationChanged,
                onChangeNumberOfRooms = onChangeNumberOfRooms,
                onChangeCategory = onChangeCategory,
                unfilter = undoFilter
            )
            Spacer(modifier = Modifier.height(16.dp))
            PropertiesDisplay(
                properties = properties.reversed(),
                navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen,
                executionStatus = ExecutionStatus.SUCCESS
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LivePropertiesScreenPreview(){
    PropEaseAdminTheme {
        VerifiedLivePropertiesScreen(
            rooms = emptyList(),
            properties = properties,
            filteringOn = false,
            selectedLocation = "",
            categories = emptyList(),
            categoryNameSelected = "",
            numberOfRoomsSelected = "",
            onSearchLocationChanged = {},
            onChangeNumberOfRooms = {},
            onChangeCategory = {},
            undoFilter = { /*TODO*/ },
            navigateToSpecificPropertyScreen = {}
        )
    }
}