package com.tms.propease_admin.ui.screen.property

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.model.PropertyDetails
import com.tms.propease_admin.nav.AppNavigation
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
    rooms = "",
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

object PropertyDetailsScreenDestination: AppNavigation{
    override val title: String = "Property details screen"
    override val route: String = "property-details-screen"
    val propertyId: String = "propertyId"
    val routeWithArgs: String = "$route/{$propertyId}"
}
@Composable
fun PropertyDetailsScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    navigateToHomeScreenWithArgs: (childScreen: String) -> Unit,
    navigateToHomeScreenWithoutArgs: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: PropertyDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var approvePropertyDialog by remember {
        mutableStateOf(false)
    }

    var disApprovePropertyDialog by remember {
        mutableStateOf(false)
    }

    if(uiState.executionStatus == ExecutionStatus.SUCCESS && uiState.propertyVerified) {
        Toast.makeText(context, "Property verified", Toast.LENGTH_SHORT).show()
        if(uiState.property.paid) {
            navigateToHomeScreenWithoutArgs()
        } else if(!uiState.property.paid) {
            navigateToHomeScreenWithArgs("verified-not-live-properties-screen")
        }
        viewModel.resetVerificationStatus()
    } else if(uiState.executionStatus == ExecutionStatus.SUCCESS && uiState.propertyUnVerified) {
        Toast.makeText(context, "Property unverified", Toast.LENGTH_SHORT).show()
        navigateToHomeScreenWithArgs("unverified-properties-screen")
        viewModel.resetVerificationStatus()
    } else if(uiState.executionStatus == ExecutionStatus.FAIL) {
        Toast.makeText(context, uiState.verificationMessage, Toast.LENGTH_SHORT).show()
        viewModel.resetVerificationStatus()
    }

    if(approvePropertyDialog) {
        AlertDialog(
            title = {
                Text(text = "Verify Property")
            },
            text = {
                Text(text = "Confirm verification decision")
            },
            onDismissRequest = { approvePropertyDialog  = !approvePropertyDialog },
            dismissButton = { 
                TextButton(onClick = { approvePropertyDialog = !approvePropertyDialog }) {
                      Text(text = "Dismiss")          
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.verifyProperty()
                    approvePropertyDialog  = !approvePropertyDialog
                }) {
                    Text(text = "Confirm")
                }
            }
        )
    }

    if(disApprovePropertyDialog) {
        AlertDialog(
            title = {
                Text(text = "Disapprove Property")
            },
            text = {
                Text(text = "Confirm disapproval")
            },
            onDismissRequest = { disApprovePropertyDialog  = !disApprovePropertyDialog },
            dismissButton = {
                TextButton(onClick = { disApprovePropertyDialog = !disApprovePropertyDialog }) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.rejectProperty()
                    disApprovePropertyDialog  = !disApprovePropertyDialog
                }) {
                    Text(text = "Confirm")
                }
            }
        )
    }

    Box {
        PropertyDetailsScreen(
            property = uiState.property,
            navigateToPreviousScreen = navigateToPreviousScreen,
            executionStatus = uiState.executionStatus,
            onVerifyPropertyClicked = {
                approvePropertyDialog = true
            },
            onRejectPropertyClicked = {
                disApprovePropertyDialog = true
            }
        )
    }
}

@Composable
fun PropertyDetailsScreen(
    property: PropertyDetails,
    executionStatus: ExecutionStatus,
    navigateToPreviousScreen: () -> Unit,
    onVerifyPropertyClicked: () -> Unit,
    onRejectPropertyClicked: () -> Unit,
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
            executionStatus = executionStatus,
            onVerifyPropertyClicked = onVerifyPropertyClicked,
            onRejectPropertyClicked = onRejectPropertyClicked
        )
    }
}





@Preview(showBackground = true)
@Composable
fun PropertyDetailsScreenPreview() {
    PropEaseAdminTheme {
        PropertyDetailsScreen(
            property = property,
            executionStatus = ExecutionStatus.INITIAL,
            navigateToPreviousScreen = {},
            onVerifyPropertyClicked = {},
            onRejectPropertyClicked = {}
        )
    }
}