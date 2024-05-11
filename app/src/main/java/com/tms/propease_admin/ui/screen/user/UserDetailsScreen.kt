//package com.tms.propease_admin.ui.screen.user
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.tms.propease_admin.R
//import com.tms.propease_admin.model.UnverifiedUserProfile
//import com.tms.propease_admin.model.UserProfile
//import com.tms.propease_admin.utils.UserDetails
//
//@Composable
//fun UserDetailsScreenComposable() {
//
//}
//
//@Composable
//fun UserDetailsScreen(
//    userProfile: UnverifiedUserProfile,
//    navigateToPreviousScreen: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = navigateToPreviousScreen) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Previous screen"
//                )
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            Text(
//                text = "VERIFICATION",
//                fontWeight = FontWeight.Bold
//            )
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(
//            text = "User's National Identification card",
//
//            )
//        Spacer(modifier = Modifier.height(20.dp))
//        Column(
//            modifier = Modifier
//                .weight(10f)
//                .verticalScroll(rememberScrollState())
//        ) {
//            Text(
//                text = "ID Front:",
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            AsyncImage(
//                model = ImageRequest.Builder(context = LocalContext.current)
//                    .data(userProfile.documents[0])
//                    .crossfade(true)
//                    .build(),
//                placeholder = painterResource(id = R.drawable.loading_img),
//                error = painterResource(id = R.drawable.ic_broken_image),
//                contentScale = ContentScale.Crop,
//                contentDescription = "Front ID",
//                modifier = Modifier
//                    .height(250.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(10.dp))
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = "ID Back:",
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            AsyncImage(
//                model = ImageRequest.Builder(context = LocalContext.current)
//                    .data(userProfile.documents[1])
//                    .crossfade(true)
//                    .build(),
//                placeholder = painterResource(id = R.drawable.loading_img),
//                error = painterResource(id = R.drawable.ic_broken_image),
//                contentScale = ContentScale.Crop,
//                contentDescription = "Front ID",
//                modifier = Modifier
//                    .height(250.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(10.dp))
//            )
//
//
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        Button(
//            enabled = saveButtonEnabled && executionStatus != ReusableFunctions.ExecutionStatus.LOADING,
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Black
//            ),
//            modifier = Modifier
//                .fillMaxWidth(),
//            onClick = onUploadDocuments
//        ) {
//            if(executionStatus == ReusableFunctions.ExecutionStatus.LOADING) {
//                CircularProgressIndicator()
//            } else {
//                Text(
//                    text = "Verify"
//                )
//            }
//        }
//    }
//}
//
