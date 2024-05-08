package com.tms.propease_admin.ui.screen.user

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tms.propease_admin.model.UserProfile
import com.tms.propease_admin.model.UserRole
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme


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
fun UnverifiedUsersScreenComposable() {
    Box {
        UnverifiedUsersScreen(
            users = users
        )
    }
}

@Composable
fun UnverifiedUsersScreen(
    users: List<UserProfile>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(users) {
                SingleUserCell(
                    userProfile = it,
                    approved = it.approved
                )
            }
        }
    }
}

@Composable
fun SingleUserCell(
    userProfile: UserProfile,
    approved: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
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
            Text(text = "Role: ${userProfile.roles[0].name}")
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
            users = users
        )
    }
}