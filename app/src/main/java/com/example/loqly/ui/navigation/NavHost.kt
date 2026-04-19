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
                startDestination = NavRoutes.Splash
            ) {
                composable<NavRoutes.Splash> {

                }

                composable<NavRoutes.Login> {

                }

                composable<NavRoutes.SignUp> {

                }
            }
        }
    }
}