package com.tms.propease_admin.ui.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tms.propease_admin.AppViewModelFactory
import com.tms.propease_admin.R
import com.tms.propease_admin.nav.AppNavigation
import com.tms.propease_admin.ui.screen.property.UnverifiedPropertiesScreenComposable
import com.tms.propease_admin.ui.screen.property.VerifiedLivePropertiesScreenComposable
import com.tms.propease_admin.ui.screen.property.VerifiedNotLivePropertiesScreenComposable
import com.tms.propease_admin.ui.screen.property.category.CategoriesScreenComposable
import com.tms.propease_admin.ui.screen.user.UnverifiedUsersScreenComposable
import com.tms.propease_admin.ui.theme.PropEaseAdminTheme
import com.tms.propease_admin.utils.ProfileScreenNavigationItem
import com.tms.propease_admin.utils.PropertyScreenNavigationItem
import com.tms.propease_admin.utils.Screen
import com.tms.propease_admin.utils.UserScreenNavigationItem
import kotlinx.coroutines.launch

object HomeScreenDestination: AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"
    val childScreen: String = "child-screen"
    val routeWithArgs: String = "$route/{$childScreen}"

}
@Composable
fun HomeScreenComposable(
    navigateToHomeScreenWithoutArgs: () -> Unit,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit
) {
    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()



    val loggedInPropertyScreens = listOf<PropertyScreenNavigationItem>(
        PropertyScreenNavigationItem(
            title = "Verified - live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_LIVE_PROPERTIES,
            color = Color.Gray
        ),
        PropertyScreenNavigationItem(
            title = "Verified - not live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_NOT_LIVE_PROPERTIES,
            color = Color.Red
        ),
        PropertyScreenNavigationItem(
            title = "Unverified properties",
            icon = R.drawable.house,
            screen = Screen.UNVERIFIED_PROPERTIES,
            color = Color.Red
        ),
        PropertyScreenNavigationItem(
            title = "Archived properties",
            icon = R.drawable.archive,
            screen = Screen.ARCHIVED_PROPERTIES,
            color = Color.Gray
        ),
        PropertyScreenNavigationItem(
            title = "Categories",
            icon = R.drawable.category,
            screen = Screen.CATEGORIES,
            color = Color.Gray
        ),
    )
    val loggedOutPropertyScreens = listOf<PropertyScreenNavigationItem>(
        PropertyScreenNavigationItem(
            title = "Verified - live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_LIVE_PROPERTIES,
            color = Color.Gray
        ),
    )
    val userScreens = listOf<UserScreenNavigationItem>(
        UserScreenNavigationItem(
            title = "Unverified users",
            icon = R.drawable.users,
            screen = Screen.UNVERIFIED_USERS,
            color = Color.Red
        ),
    )

    val loggedInProfileScreens = listOf<ProfileScreenNavigationItem>(
        ProfileScreenNavigationItem(
            title = "Notifications",
            icon = R.drawable.notifications,
            screen = Screen.NOTIFICATIONS,
            color = Color.Gray
        ),
        ProfileScreenNavigationItem(
            title = "Profile",
            icon = R.drawable.profile,
            screen = Screen.PROFILE,
            color = Color.Gray
        ),
        ProfileScreenNavigationItem(
            title = "Log out",
            icon = R.drawable.logout,
            screen = Screen.LOGOUT,
            color = Color.Gray
        ),
    )
    val loggedOutProfileScreens = listOf<ProfileScreenNavigationItem>(
        ProfileScreenNavigationItem(
            title = "Log in",
            icon = R.drawable.login,
            screen = Screen.LOGIN,
            color = Color.Gray
        ),
    )

    Box {
        HomeScreen(
            userName = uiState.userDetails.userName,
            loggedIn = uiState.userDetails.userId != null,
            childScreen = uiState.childScreen,
            loggedInPropertyScreens = loggedInPropertyScreens,
            loggedOutPropertyScreens = loggedOutPropertyScreens,
            userScreens = userScreens,
            loggedInProfileScreens = loggedInProfileScreens,
            loggedOutProfileScreens = loggedOutProfileScreens,
            navigateToHomeScreenWithoutArgs = navigateToHomeScreenWithoutArgs,
            navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen,
            clearChildScreen = {
                viewModel.clearChildScreen()
            }
        )
    }
}

@Composable
fun HomeScreen(
    userName: String,
    loggedIn: Boolean,
    childScreen: String,
    clearChildScreen: () -> Unit,
    loggedInPropertyScreens: List<PropertyScreenNavigationItem>,
    loggedOutPropertyScreens: List<PropertyScreenNavigationItem>,
    userScreens: List<UserScreenNavigationItem>,
    loggedInProfileScreens: List<ProfileScreenNavigationItem>,
    loggedOutProfileScreens: List<ProfileScreenNavigationItem>,
    navigateToHomeScreenWithoutArgs: () -> Unit,
    navigateToSpecificPropertyScreen: (propertyId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by rememberSaveable {
        mutableStateOf(Screen.VERIFIED_LIVE_PROPERTIES)
    }
    var screenLabel by rememberSaveable {
        mutableStateOf("Properties")
    }

    if(childScreen == "verified-not-live-properties-screen") {
        currentScreen = Screen.VERIFIED_NOT_LIVE_PROPERTIES
        clearChildScreen()
    } else if(childScreen == "unverified-properties-screen") {
        currentScreen = Screen.UNVERIFIED_PROPERTIES
        clearChildScreen()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                top = 16.dp
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_placeholder),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(90.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = userName,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider()

                    // starts here
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Property".uppercase(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                        if(loggedIn) {
                            for(menuItem in loggedInPropertyScreens) {
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                tint = menuItem.color,
                                                painter = painterResource(id = menuItem.icon),
                                                contentDescription = menuItem.title
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = menuItem.title)
                                        }
                                    },
                                    selected = menuItem.screen == currentScreen,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            currentScreen = menuItem.screen
                                        }

                                    }
                                )
                            }
                        } else {
                            for(menuItem in loggedOutPropertyScreens) {
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                tint = menuItem.color,
                                                painter = painterResource(id = menuItem.icon),
                                                contentDescription = menuItem.title
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = menuItem.title)
                                        }
                                    },
                                    selected = menuItem.screen == currentScreen,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            currentScreen = menuItem.screen
                                        }

                                    }
                                )
                            }
                        }
                        if(loggedIn) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Users".uppercase(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            for(menuItem in userScreens) {
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                tint = menuItem.color,
                                                painter = painterResource(id = menuItem.icon),
                                                contentDescription = menuItem.title
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = menuItem.title)
                                        }
                                    },
                                    selected = menuItem.screen == currentScreen,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            currentScreen = menuItem.screen
                                        }

                                    }
                                )
                            }


                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Profile".uppercase(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                        if(loggedIn) {
                            for(menuItem in loggedInProfileScreens) {
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                tint = menuItem.color,
                                                painter = painterResource(id = menuItem.icon),
                                                contentDescription = menuItem.title
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = menuItem.title)
                                        }
                                    },
                                    selected = menuItem.screen == currentScreen,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            currentScreen = menuItem.screen
                                        }

                                    }
                                )
                            }
                        } else {
                            for(menuItem in loggedOutProfileScreens) {
                                NavigationDrawerItem(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    label = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                tint = menuItem.color,
                                                painter = painterResource(id = menuItem.icon),
                                                contentDescription = menuItem.title
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(text = menuItem.title)
                                        }
                                    },
                                    selected = menuItem.screen == currentScreen,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            currentScreen = menuItem.screen
                                        }

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    scope.launch {
                        if(drawerState.isClosed) drawerState.open() else drawerState.close()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Navigation menu"
                    )

                }
                Text(
                    text = "Hey, $userName",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = screenLabel,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            when(currentScreen) {
                Screen.VERIFIED_LIVE_PROPERTIES -> {
                    VerifiedLivePropertiesScreenComposable()
                }
                Screen.VERIFIED_NOT_LIVE_PROPERTIES -> {
                    VerifiedNotLivePropertiesScreenComposable()
                }
                Screen.UNVERIFIED_PROPERTIES -> {
                    UnverifiedPropertiesScreenComposable(
                        navigateToHomeScreenWithoutArgs = navigateToHomeScreenWithoutArgs,
                        navigateToSpecificPropertyScreen = navigateToSpecificPropertyScreen
                    )
                }
                Screen.ARCHIVED_PROPERTIES -> {
                }
                Screen.CATEGORIES -> {
                    CategoriesScreenComposable()
                }
                Screen.UNVERIFIED_USERS -> {
                    UnverifiedUsersScreenComposable()
                }
                Screen.NOTIFICATIONS -> {}
                Screen.PROFILE -> {}
                Screen.LOGIN -> {}
                Screen.LOGOUT -> {}
                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val loggedInPropertyScreens = listOf<PropertyScreenNavigationItem>(
        PropertyScreenNavigationItem(
            title = "Verified - live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_LIVE_PROPERTIES,
            color = Color.Gray
        ),
        PropertyScreenNavigationItem(
            title = "Verified - not live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_NOT_LIVE_PROPERTIES,
            color = Color.Red
        ),
        PropertyScreenNavigationItem(
            title = "Unverified properties",
            icon = R.drawable.house,
            screen = Screen.UNVERIFIED_PROPERTIES,
            color = Color.Red
        ),
        PropertyScreenNavigationItem(
            title = "Archived properties",
            icon = R.drawable.archive,
            screen = Screen.ARCHIVED_PROPERTIES,
            color = Color.Gray
        ),
        PropertyScreenNavigationItem(
            title = "Categories",
            icon = R.drawable.category,
            screen = Screen.CATEGORIES,
            color = Color.Gray
        ),
    )
    val loggedOutPropertyScreens = listOf<PropertyScreenNavigationItem>(
        PropertyScreenNavigationItem(
            title = "Verified - live properties",
            icon = R.drawable.house,
            screen = Screen.VERIFIED_LIVE_PROPERTIES,
            color = Color.Gray
        ),
    )
    val userScreens = listOf<UserScreenNavigationItem>(
        UserScreenNavigationItem(
            title = "Unverified users",
            icon = R.drawable.users,
            screen = Screen.UNVERIFIED_USERS,
            color = Color.Red
        ),
    )

    val loggedInProfileScreens = listOf<ProfileScreenNavigationItem>(
        ProfileScreenNavigationItem(
            title = "Notifications",
            icon = R.drawable.notifications,
            screen = Screen.NOTIFICATIONS,
            color = Color.Gray
        ),
        ProfileScreenNavigationItem(
            title = "Profile",
            icon = R.drawable.profile,
            screen = Screen.PROFILE,
            color = Color.Gray
        ),
        ProfileScreenNavigationItem(
            title = "Log out",
            icon = R.drawable.logout,
            screen = Screen.LOGOUT,
            color = Color.Gray
        ),
    )
    val loggedOutProfileScreens = listOf<ProfileScreenNavigationItem>(
        ProfileScreenNavigationItem(
            title = "Log out",
            icon = R.drawable.login,
            screen = Screen.LOGIN,
            color = Color.Gray
        ),
    )
    PropEaseAdminTheme {
        HomeScreen(
            userName = "Alex Mbogo",
            loggedIn = true,
            childScreen = "",
            loggedInPropertyScreens = loggedInPropertyScreens,
            loggedOutPropertyScreens = loggedOutPropertyScreens,
            userScreens = userScreens,
            loggedInProfileScreens = loggedInProfileScreens,
            loggedOutProfileScreens = loggedOutProfileScreens,
            navigateToHomeScreenWithoutArgs = {},
            navigateToSpecificPropertyScreen = {},
            clearChildScreen = {}
        )
    }
}