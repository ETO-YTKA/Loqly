package com.example.loqly.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loqly.ui.screens.chats.ChatsScreen
import com.example.loqly.ui.screens.login.LoginScreen
import com.example.loqly.ui.screens.signup.SignUpScreen

private const val EnterDurationMillis = 280
private const val ExitDurationMillis = 220
private const val SlideOffsetDivisor = 5

private fun AnimatedContentTransitionScope<*>.softSharedAxisEnter(forward: Boolean): EnterTransition {
    val offset: (Int) -> Int = { distance ->
        val direction = if (forward) 1 else -1
        (distance / SlideOffsetDivisor) * direction
    }

    return slideInHorizontally(
        initialOffsetX = offset,
        animationSpec = tween(
            durationMillis = EnterDurationMillis,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = EnterDurationMillis,
            easing = FastOutSlowInEasing
        )
    )
}

private fun AnimatedContentTransitionScope<*>.softSharedAxisExit(forward: Boolean): ExitTransition {
    val offset: (Int) -> Int = { distance ->
        val direction = if (forward) -1 else 1
        (distance / SlideOffsetDivisor) * direction
    }

    return slideOutHorizontally(
        targetOffsetX = offset,
        animationSpec = tween(
            durationMillis = ExitDurationMillis,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = ExitDurationMillis,
            easing = FastOutSlowInEasing
        )
    )
}

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
                startDestination = NavRoutes.Login,
                enterTransition = { softSharedAxisEnter(forward = true) },
                exitTransition = { softSharedAxisExit(forward = true) },
                popEnterTransition = { softSharedAxisEnter(forward = false) },
                popExitTransition = { softSharedAxisExit(forward = false) }
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
                    SignUpScreen(
                        popBackStack = { navController.popBackStack() }
                    )
                }

                composable<NavRoutes.ForgotPassword> {

                }

                composable<NavRoutes.Chats> {
                    ChatsScreen()
                }
            }
        }
    }
}
