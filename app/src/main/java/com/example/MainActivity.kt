package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.FeedScreen
import com.example.ui.screens.JobDetailScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MyProfileScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.PostScreen
import com.example.ui.screens.ProfileDetailScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AjiraViewModel

sealed interface AppDestination {
    object Splash : AppDestination
    object Login : AppDestination
    object Main : AppDestination
    object JobDetail : AppDestination
    object ProfileDetail : AppDestination
    object Post : AppDestination
}

enum class BottomNavTab(val title: String) {
    HOME("Nyumbani"),
    NOTIFICATIONS("Arifa"),
    PROFILE("Wasifu Wangu")
}

class MainActivity : ComponentActivity() {

    private val viewModel: AjiraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                ClipZoneAjiraApp(viewModel)
            }
        }
    }
}

@Composable
fun ClipZoneAjiraApp(viewModel: AjiraViewModel) {
    var currentDestination by remember { mutableStateOf<AppDestination>(AppDestination.Splash) }
    var selectedBottomTab by remember { mutableStateOf(BottomNavTab.HOME) }

    val selectedJob by viewModel.selectedJob.collectAsState()
    val selectedProfile by viewModel.selectedProfile.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AnimatedContent(
            targetState = currentDestination,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition"
        ) { destination ->
            when (destination) {
                AppDestination.Splash -> {
                    SplashScreen(
                        onTimeout = {
                            currentDestination = AppDestination.Main
                        }
                    )
                }

                AppDestination.Login -> {
                    LoginScreen(
                        onLoginSuccess = { phone, email, name, role ->
                            viewModel.loginUser(phone, email, name, role)
                            currentDestination = AppDestination.Main
                        },
                        onSkip = {
                            currentDestination = AppDestination.Main
                        }
                    )
                }

                AppDestination.Main -> {
                    Scaffold(
                        bottomBar = {
                            NavigationBar(
                                containerColor = Color.White,
                                tonalElevation = 8.dp,
                                modifier = Modifier.testTag("bottom_navigation_bar")
                            ) {
                                // Tab 1: Nyumbani
                                val isHomeSelected = selectedBottomTab == BottomNavTab.HOME
                                NavigationBarItem(
                                    selected = isHomeSelected,
                                    onClick = { selectedBottomTab = BottomNavTab.HOME },
                                    icon = {
                                        Icon(
                                            imageVector = if (isHomeSelected) Icons.Filled.Home else Icons.Outlined.Home,
                                            contentDescription = "Nyumbani"
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = "Nyumbani",
                                            fontSize = 12.sp,
                                            fontWeight = if (isHomeSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = BrandGreenDark,
                                        selectedTextColor = BrandGreenDark,
                                        indicatorColor = BrandGreenLight,
                                        unselectedIconColor = Color(0xFF64748B),
                                        unselectedTextColor = Color(0xFF64748B)
                                    )
                                )

                                // Tab 2: Arifa
                                val isNotifSelected = selectedBottomTab == BottomNavTab.NOTIFICATIONS
                                NavigationBarItem(
                                    selected = isNotifSelected,
                                    onClick = { selectedBottomTab = BottomNavTab.NOTIFICATIONS },
                                    icon = {
                                        Icon(
                                            imageVector = if (isNotifSelected) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                            contentDescription = "Arifa"
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = "Arifa",
                                            fontSize = 12.sp,
                                            fontWeight = if (isNotifSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = BrandGreenDark,
                                        selectedTextColor = BrandGreenDark,
                                        indicatorColor = BrandGreenLight,
                                        unselectedIconColor = Color(0xFF64748B),
                                        unselectedTextColor = Color(0xFF64748B)
                                    )
                                )

                                // Tab 3: Wasifu Wangu
                                val isProfileSelected = selectedBottomTab == BottomNavTab.PROFILE
                                NavigationBarItem(
                                    selected = isProfileSelected,
                                    onClick = { selectedBottomTab = BottomNavTab.PROFILE },
                                    icon = {
                                        Icon(
                                            imageVector = if (isProfileSelected) Icons.Filled.Person else Icons.Outlined.Person,
                                            contentDescription = "Wasifu Wangu"
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = "Wasifu",
                                            fontSize = 12.sp,
                                            fontWeight = if (isProfileSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = BrandGreenDark,
                                        selectedTextColor = BrandGreenDark,
                                        indicatorColor = BrandGreenLight,
                                        unselectedIconColor = Color(0xFF64748B),
                                        unselectedTextColor = Color(0xFF64748B)
                                    )
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            when (selectedBottomTab) {
                                BottomNavTab.HOME -> {
                                    FeedScreen(
                                        viewModel = viewModel,
                                        onJobClick = {
                                            currentDestination = AppDestination.JobDetail
                                        },
                                        onProfileClick = {
                                            currentDestination = AppDestination.ProfileDetail
                                        },
                                        onAddClick = {
                                            currentDestination = AppDestination.Post
                                        },
                                        onRoleBadgeClick = {
                                            selectedBottomTab = BottomNavTab.PROFILE
                                        }
                                    )
                                }

                                BottomNavTab.NOTIFICATIONS -> {
                                    NotificationsScreen(viewModel = viewModel)
                                }

                                BottomNavTab.PROFILE -> {
                                    MyProfileScreen(
                                        viewModel = viewModel
                                    )
                                }
                            }
                        }
                    }
                }

                AppDestination.JobDetail -> {
                    selectedJob?.let { job ->
                        JobDetailScreen(
                            job = job,
                            onBack = { currentDestination = AppDestination.Main }
                        )
                    } ?: run {
                        currentDestination = AppDestination.Main
                    }
                }

                AppDestination.ProfileDetail -> {
                    selectedProfile?.let { profile ->
                        ProfileDetailScreen(
                            profile = profile,
                            onBack = { currentDestination = AppDestination.Main }
                        )
                    } ?: run {
                        currentDestination = AppDestination.Main
                    }
                }

                AppDestination.Post -> {
                    PostScreen(
                        viewModel = viewModel,
                        onBack = { currentDestination = AppDestination.Main },
                        onSuccess = { currentDestination = AppDestination.Main }
                    )
                }
            }
        }
    }
}
