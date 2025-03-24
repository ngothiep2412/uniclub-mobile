package com.example.unihub.uniclub.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unihub.ui.theme.ComposeTheme
import com.example.unihub.uniclub.data.network.TokenExpirationHandler
import com.example.unihub.uniclub.navigation.graph.authNav
import com.example.unihub.uniclub.navigation.model.Screen
import com.example.unihub.uniclub.presentation.main.MainScreen
import com.example.unihub.uniclub.presentation.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import org.koin.compose.koinInject

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    onDataLoaded: () -> Unit,
    tokenExpirationHandler: TokenExpirationHandler = koinInject()
) {

    var fakeLoading by remember { mutableStateOf(true) }
    // TODO: Check session token

    val session = viewModel.getSession().observeAsState()
    Timber.d("Session: ${session.value}")


    // Listen for token expiration events
    LaunchedEffect(key1 = Unit) {
        tokenExpirationHandler.tokenExpired.collectLatest {
            // Navigate to auth screen when token expires
            Timber.d("Token expired, navigating to auth screen")
            navController.navigate(Screen.AuthNav.route) {
                // Clear the back stack
                popUpTo(Screen.Root.route) {
                    inclusive = true
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        delay(1000)
        fakeLoading = false
        onDataLoaded()
    }

    if (!fakeLoading) {
        when {
            session.value == null || session.value!!.isEmpty() -> {
                NavHost(
                    navController = navController,
                    route = Screen.Root.route,
                    startDestination = Screen.AuthNav.route
                ) {
                    authNav(navController = navController)

                    composable(
                        route = Screen.MainNav.route,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(500)
                            )
                        },
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(500)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(500)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(500)
                            )
                        }
                    ) {
                        MainScreen()
                    }
                }
            }

            else ->
                NavHost(
                    navController = navController,
                    route = Screen.Root.route,
                    startDestination = Screen.MainNav.route
                ) {
                    authNav(navController = navController)
                    composable(
                        route = Screen.MainNav.route,
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(500)
                            )
                        },
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(500)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(500)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(500)
                            )
                        }
                    ) {
                        MainScreen()
                    }
                }
        }
    }

}

@Preview
@Composable
private fun RootNavigationGraphPreview() {
    ComposeTheme {
        RootNavigationGraph(
            navController = rememberNavController(),
            viewModel = koinViewModel(),
            tokenExpirationHandler = koinInject(),
            onDataLoaded = {},
        )
    }
}