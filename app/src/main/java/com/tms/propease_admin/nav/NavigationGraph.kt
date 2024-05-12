package com.tms.propease_admin.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tms.propease_admin.ui.screen.HomeScreenComposable
import com.tms.propease_admin.ui.screen.HomeScreenDestination
import com.tms.propease_admin.ui.screen.SplashScreenComposable
import com.tms.propease_admin.ui.screen.SplashScreenDestination
import com.tms.propease_admin.ui.screen.accountManagement.LoginScreenComposable
import com.tms.propease_admin.ui.screen.accountManagement.LoginScreenDestination
import com.tms.propease_admin.ui.screen.accountManagement.RegistrationScreenComposable
import com.tms.propease_admin.ui.screen.accountManagement.RegistrationScreenDestination
import com.tms.propease_admin.ui.screen.property.PropertyDetailsScreenComposable
import com.tms.propease_admin.ui.screen.property.PropertyDetailsScreenDestination
import com.tms.propease_admin.ui.screen.user.UserDetailsScreenComposable
import com.tms.propease_admin.ui.screen.user.UserDetailsScreenDestination

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
        modifier = modifier
    ) {
        composable(SplashScreenDestination.route) {
            SplashScreenComposable(
                navigateToRegistrationScreen = {
                    navController.popBackStack(SplashScreenDestination.route, true)
                    navController.navigate(RegistrationScreenDestination.route)
                },
                navigateToHomeScreen = {
                    navController.popBackStack(SplashScreenDestination.route, true)
                    navController.navigate(HomeScreenDestination.route)
                },
            )
        }
        composable(RegistrationScreenDestination.route) {
            RegistrationScreenComposable(
                navigateToLoginScreenWithArgs = {phoneNumber, password ->
                    navController.popBackStack(RegistrationScreenDestination.route, true)
                    navController.navigate("${LoginScreenDestination.route}/${phoneNumber}/${password}")
                },
                navigateToLoginScreenWithoutArgs = {
                    navController.navigate(LoginScreenDestination.route)
                }
            )
        }
        composable(
            LoginScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(LoginScreenDestination.phoneNumber) {
                    type = NavType.StringType
                },
                navArgument(LoginScreenDestination.password) {
                    type = NavType.StringType
                }
            )
        ) {
            LoginScreenComposable(
                navigateToHomeScreenWithoutArgs = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(LoginScreenDestination.route) {
            LoginScreenComposable(
                navigateToHomeScreenWithoutArgs = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(HomeScreenDestination.route) {
            HomeScreenComposable(
                navigateToHomeScreenWithoutArgs = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToSpecificPropertyScreen = {
                    navController.navigate("${PropertyDetailsScreenDestination.route}/${it}")
                },
                navigateToSpecificUser = {
                    navController.navigate("${UserDetailsScreenDestination.route}/${it}")
                },
                navigateToHomeScreenWithArgs = {
                    navController.navigate("${HomeScreenDestination.route}/${it}")
                }
            )
        }
        composable(
            PropertyDetailsScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PropertyDetailsScreenDestination.propertyId) {
                    type = NavType.StringType
                }
            )
        ) {
            PropertyDetailsScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                },
                navigateToHomeScreenWithoutArgs = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToHomeScreenWithArgs = {
                    navController.navigate("${HomeScreenDestination.route}/${it}")
                }
            )
        }
        composable(
            HomeScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(HomeScreenDestination.childScreen) {
                    type = NavType.StringType
                }
            )
        ) {
            HomeScreenComposable(
                navigateToHomeScreenWithoutArgs = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToSpecificPropertyScreen = {
                    navController.navigate("${PropertyDetailsScreenDestination.route}/${it}")
                },
                navigateToSpecificUser = {
                    navController.navigate("${UserDetailsScreenDestination.route}/${it}")
                },
                navigateToHomeScreenWithArgs = {
                    navController.navigate("${HomeScreenDestination.route}/${it}")
                }
            )
        }
        composable(
            UserDetailsScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UserDetailsScreenDestination.userId) {
                    type = NavType.StringType
                }
            )
        ) {
            UserDetailsScreenComposable(
                navigateToHomeScreenWithArgs = {
                    navController.navigate("${HomeScreenDestination.route}/${it}")
                },
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
    }
}