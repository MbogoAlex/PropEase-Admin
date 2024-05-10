package com.tms.propease_admin.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tms.propease_admin.nav.NavigationGraph

@Composable
fun StartScreen(
    navController: NavHostController = rememberNavController()
) {
    NavigationGraph(navController = navController)
}