package com.example.unihub.uniclub.presentation.main.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unihub.R
import com.example.unihub.ui.theme.ComposeTheme
import com.example.unihub.uniclub.navigation.model.NavigationItem
import com.example.unihub.uniclub.navigation.model.Screen

@Composable
fun BottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .height(75.dp)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0xFFE3E3E3),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2f
                )
            }
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = "Home",
                icon = painterResource(R.drawable.icon_home_outlined),
                iconActive = painterResource(R.drawable.icon_home_filled),
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Home",
                icon = painterResource(R.drawable.icon_home_outlined),
                iconActive = painterResource(R.drawable.icon_home_filled),
                screen = Screen.Home
            ),

            NavigationItem(
                title = "Home",
                icon = painterResource(R.drawable.icon_home_outlined),
                iconActive = painterResource(R.drawable.icon_home_filled),
                screen = Screen.Home
            ),
//
//            NavigationItem(
//                title = "Profile",
//                icon = painterResource(R.drawable.icon_profile_outlined),
//                iconActive = painterResource(R.drawable.icon_profile_filled),
//
//                screen = Screen.Home
//            ),
        )

        navigationItem.map {

        }

    }
}


@Preview
@Composable
private fun BottomNavigationPreview() {
    ComposeTheme(
        dynamicColor = false
    ) {
        BottomNavigation(rememberNavController())
    }
}