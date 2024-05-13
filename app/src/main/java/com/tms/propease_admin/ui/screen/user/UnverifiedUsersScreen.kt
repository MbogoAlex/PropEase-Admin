package com.tms.propease_admin.ui.screen.user

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.model.UserRole
import com.tms.propease_admin.ui.screen.property.UnverifiedPropertiesScreenViewModel
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ExecutionStatus
import kotlinx.coroutines.delay


val roles = listOf<UserRole>(
    UserRole(
        id = 1,
        name = "USER"
    )
)

val users = listOf<UserProfile>(
    UserProfile(
        id = 1,
        email = "abc@gmail.com",
        phoneNumber = "12345678",
        imageUrl = "",
        approvalStatus = "UNAPPROVED",
        approved = false,
        roles = roles,
        fname = "James",
        lname = "Michael"
    ),
    UserProfile(
        id = 1,
        email = "abc@gmail.com",
        phoneNumber = "12345678",
        imageUrl = "",
        approvalStatus = "UNAPPROVED",
        approved = false,
        roles = roles,
        fname = "James",
        lname = "Michael"
    ),
    UserProfile(
        id = 1,
        email = "abc@gmail.com",
        phoneNumber = "12345678",
        imageUrl = "",
        approvalStatus = "UNAPPROVED",
        approved = false,
        roles = roles,
        fname = "James",
        lname = "Michael"
    ),
    UserProfile(
        id = 1,
        email = "abc@gmail.com",
        phoneNumber = "12345678",
        imageUrl = "",
        approvalStatus = "UNAPPROVED",
        approved = false,
        roles = roles,
        fname = "James",
        lname = "Michael"
    ),
    UserProfile(
        id = 1,
        email = "abc@gmail.com",
        phoneNumber = "12345678",
        imageUrl = "",
        approvalStatus = "UNAPPROVED",
        approved = false,
        roles = roles,
        fname = "James",
        lname = "Michael"
    ),

)
@Composable
fun UnverifiedUsersScreenComposable(
    navigateToHomeScreenWithoutArgs: () -> Unit,
    navigateToSpecificUser: (userId: String) -> Unit,
) {
    BackHandler(onBack = navigateToHomeScreenWithoutArgs)
    val viewModel: UnverifiedUsersScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()


    Box {
        UnverifiedUsersScreen(
            executionStatus = uiState.executionStatus,
            navigateToSpecificUser = navigateToSpecificUser,
            users = uiState.unverifiedUsers
        )
    }
}

@Composable
fun UnverifiedUsersScreen(
    users: List<UserProfile>,
    executionStatus: ExecutionStatus,
    navigateToSpecificUser: (userId: String) -> Unit,
    modifier: Modifier = Modifier
) {

    var showText by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Unverified users",
            fontWeight = FontWeight.Bold
        )
        if(executionStatus == ExecutionStatus.SUCCESS) {
            if(users.isEmpty()) {
                LaunchedEffect(Unit) {
                    delay(2000L)
                    showText = true
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if(showText) {
                        Text(text = "No unverified user")
                    } else {
                        CircularProgressIndicator()
                    }
                }
            } else {
                LazyColumn {
                    items(users.size) {
                        SingleUserCell(
                            userProfile = users[it],
                            approved = users[it].approved,
                            navigateToSpecificUser = navigateToSpecificUser,
                            index = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleUserCell(
    userProfile: UserProfile,
    approved: Boolean,
    navigateToSpecificUser: (userId: String) -> Unit,
    index: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { navigateToSpecificUser(index.toString()) }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
                Text(
                    text = "${userProfile.fname} ${userProfile.lname}",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Role: ${userProfile.roles[0].name}")
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navigateToSpecificUser(index.toString())
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "${userProfile.fname} ${userProfile.lname}"
                    )
                }
            }
            if(!approved) {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Gray
                    ),
                ) {
                    Text(
                        text = "Unverified".uppercase(),
                        fontSize = 11.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 5.dp,
                                end = 10.dp,
                                bottom = 5.dp,
                            )

                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnverifiedUsersScreenPreview() {
    PropEaseAdminTheme {
        UnverifiedUsersScreen(
            executionStatus = ExecutionStatus.INITIAL,
            users = users,
            navigateToSpecificUser = {}
        )
    }
}