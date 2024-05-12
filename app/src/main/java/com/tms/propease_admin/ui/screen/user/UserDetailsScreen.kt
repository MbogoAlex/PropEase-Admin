package com.tms.propease_admin.ui.screen.user

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.model.UserVerificationProfile
import com.tms.propease_admin.nav.AppNavigation
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.VerificationStatus

object UserDetailsScreenDestination: AppNavigation{
    override val title: String = "User details screen"
    override val route: String = "user-details-screen"
    val userId: String = "userId"
    val routeWithArgs: String = "$route/{$userId}"
}
@Composable
fun UserDetailsScreenComposable(
    navigateToHomeScreenWithArgs: (childScreen: String) -> Unit,
    navigateToPreviousScreen: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: UserDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Log.i("UNVERIFIED_USER", uiState.unverifiedUser.toString())

    if(uiState.verificationStatus == VerificationStatus.SUCCESS) {
        Toast.makeText(context, "User verified", Toast.LENGTH_SHORT).show()
        navigateToHomeScreenWithArgs("unverified-users-screen")
        viewModel.resetVerificationStatus()
    } else if(uiState.verificationStatus == VerificationStatus.FAIL) {
        Toast.makeText(context, "Failed. Try again later", Toast.LENGTH_SHORT).show()
        viewModel.resetVerificationStatus()
    }

    Box {
        UserDetailsScreen(
            idFront = uiState.idFront,
            idBack = uiState.idBack,
            userProfile = uiState.unverifiedUser,
            navigateToPreviousScreen = navigateToPreviousScreen,
            onVerifyUser = {
                           viewModel.verifyUser()
            },
            verificationStatus = uiState.verificationStatus
        )
    }
}

@Composable
fun UserDetailsScreen(
    idFront: String,
    idBack: String,
    userProfile: UserVerificationProfile,
    navigateToPreviousScreen: () -> Unit,
    onVerifyUser: () -> Unit,
    verificationStatus: VerificationStatus,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "VERIFICATION",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "APPLICANT: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = "${userProfile.user.fname} ${userProfile.user.lname}")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "EMAIL: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = userProfile.user.email)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PHONE: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = userProfile.user.phoneNumber)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "User's National Identification card",

            )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .weight(10f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "ID Front:",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(idFront)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = "Front ID",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "ID Back:",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(idBack)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = "Front ID",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            )


        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = verificationStatus != VerificationStatus.LOADING,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onVerifyUser
        ) {
            if(verificationStatus == VerificationStatus.LOADING) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Verify"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    PropEaseAdminTheme {
        UserDetailsScreen(
            userProfile = unverifiedUserDt,
            navigateToPreviousScreen = { /*TODO*/ },
            onVerifyUser = { /*TODO*/ },
            idFront = "",
            idBack = "",
            verificationStatus = VerificationStatus.INITIAL
        )
    }
}


