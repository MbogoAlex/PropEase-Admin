package com.tms.propease_admin.ui.screen.accountManagement

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.userProfile

@Composable
fun ProfileScreenComposable(
    navigateToLoginScreenWithoutArgs: () -> Unit,
    navigateToLoginScreenWithArgs: (phoneNumber: String, password: String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: ProfileScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Box {
        ProfileScreen(
            profile = uiState.profile,
            onLogout = {
                viewModel.logout()
                Toast.makeText(context, "You are logged out", Toast.LENGTH_SHORT).show()
                navigateToLoginScreenWithArgs(
                    uiState.userDetails.phoneNumber,
                    uiState.userDetails.password
                )
            }
        )

    }
}

@Composable
fun ProfileScreen(
    profile: UserProfile,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        if(profile.imageUrl != null) {
            Image(
                painter = rememberImagePainter(data = profile.imageUrl),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
            )
        }

        TextButton(onClick = {
//            galleryLauncher.launch("image/*")
        }) {
            Text(
                text = "Change pic"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Personal Information",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {

                Text(
                    text = "Name",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Email",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Phone No.",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(text = "${profile.fname} ${profile.lname}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = profile.email)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = profile.phoneNumber)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Edit name"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "Edit name"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = "Edit name"
                )

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "Update profile")
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onLogout
        ) {
            Text(text = "Logout")
        }

    }
}

@Composable
fun ProfileScreenPreview() {
    PropEaseAdminTheme {
        ProfileScreen(
            profile = userProfile,
            onLogout = { /*TODO*/ }
        )
    }
}