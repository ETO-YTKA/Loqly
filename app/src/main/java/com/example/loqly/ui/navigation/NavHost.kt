package com.example.loqly.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loqly.ui.screens.login.LoginScreen

@Composable
fun LoqlyApp() {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavRoutes.Login
            ) {
                composable<NavRoutes.Splash> {

                }

                composable<NavRoutes.Login> {
                    LoginScreen(
                        navigateToChats = { navController.navigate(NavRoutes.Chats) },
                        navigateToSignUp = { navController.navigate(NavRoutes.SignUp) },
                        navigateToForgotPassword = { navController.navigate(NavRoutes.ForgotPassword) }
                    )
                }

                composable<NavRoutes.SignUp> {

                }

                composable<NavRoutes.ForgotPassword> {

                }

                composable<NavRoutes.Chats> {

                }
            }
        }
    }
}
