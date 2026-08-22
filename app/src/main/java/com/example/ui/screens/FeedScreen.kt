package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.data.model.UserRole
import com.example.ui.components.AppTopHeader
import com.example.ui.components.FeedTabSelector
import com.example.ui.components.JobCard
import com.example.ui.components.JobSeekerCard
import com.example.ui.components.LocationAndCategoryFilterBar
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium
import com.example.ui.viewmodel.AjiraViewModel
import com.example.ui.viewmodel.FeedTab
import com.example.util.AppLanguage
import com.example.util.AppStrings

/**
 * 2. FeedScreen.kt - SCREEN KUU YA CLIPZONE AJIRA (Clean, Minimal, No Search, Bilingual)
 */
@Composable
fun FeedScreen(
    viewModel: AjiraViewModel,
    onJobClick: (Job) -> Unit,
    onProfileClick: (JobSeekerProfile) -> Unit,
    onAddClick: () -> Unit,
    onRoleBadgeClick: () -> Unit
) {
    val appLanguage by viewModel.appLanguage.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val selectedLocation by viewModel.selectedLocation.collectAsState()
    val isOffline by viewModel.isOfflineMode.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val jobs by viewModel.filteredJobs.collectAsState()
    val profiles by viewModel.filteredProfiles.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                AppTopHeader(
                    isOffline = isOffline,
                    appLanguage = appLanguage,
                    onLanguageToggle = {
                        viewModel.setAppLanguage(
                            if (appLanguage == AppLanguage.SWAHILI) AppLanguage.ENGLISH else AppLanguage.SWAHILI
                        )
                    },
                    userRoleText = currentUser.role.getDisplayName(appLanguage),
                    onRoleClick = onRoleBadgeClick
                )

                // Tab 2 Kuu (WATAFUTA KAZI / WAJIRI)
                FeedTabSelector(
                    selectedTab = selectedTab,
                    jobSeekersCount = profiles.size,
                    employersCount = jobs.size,
                    appLanguage = appLanguage,
                    onTabSelected = { viewModel.setSelectedTab(it) }
                )

                // Filter Bar ya Mikoa Pekee (Bila Search Bar)
                LocationAndCategoryFilterBar(
                    selectedLocation = selectedLocation,
                    appLanguage = appLanguage,
                    onLocationSelected = { viewModel.setSelectedLocation(it) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = BrandGreen,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.testTag("fab_add_post")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = if (currentUser.role == UserRole.EMPLOYER) AppStrings.postJobOption(appLanguage) else AppStrings.postProfileOption(appLanguage),
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    if (targetState == FeedTab.EMPLOYERS) {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> -width } + fadeOut()
                        )
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut()
                        )
                    }
                },
                label = "feed_slide_transition"
            ) { targetTab ->
                when (targetTab) {
                    FeedTab.JOB_SEEKERS -> {
                        // Orodha Safi ya Watafuta Kazi
                        if (profiles.isEmpty()) {
                            EmptyFeedState(
                                title = AppStrings.emptyProfiles(appLanguage),
                                subtitle = if (appLanguage == AppLanguage.SWAHILI) "Kuwa wa kwanza kuweka wasifu wako au chagua mkoa mwingine." else "Be the first to post your profile or choose another region."
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .testTag("lazy_column_job_seekers"),
                                contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                            ) {
                                items(
                                    items = profiles,
                                    key = { it.id }
                                ) { profile ->
                                    JobSeekerCard(
                                        profile = profile,
                                        appLanguage = appLanguage,
                                        onClick = {
                                            viewModel.selectProfile(profile)
                                            onProfileClick(profile)
                                        },
                                        onContactClick = {
                                            // Piga simu moja kwa moja
                                            val phone = profile.phone.ifBlank { profile.email }
                                            if (phone.isNotBlank()) {
                                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                                    data = Uri.parse("tel:$phone")
                                                }
                                                try {
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    viewModel.selectProfile(profile)
                                                    onProfileClick(profile)
                                                }
                                            } else {
                                                viewModel.selectProfile(profile)
                                                onProfileClick(profile)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    FeedTab.EMPLOYERS -> {
                        // Orodha Safi ya Nafasi za Ajira
                        if (jobs.isEmpty()) {
                            EmptyFeedState(
                                title = AppStrings.emptyJobs(appLanguage),
                                subtitle = if (appLanguage == AppLanguage.SWAHILI) "Kuwa wa kwanza kuweka tangazo la kazi au chagua mkoa mwingine." else "Be the first to post a job or choose another region."
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .testTag("lazy_column_jobs"),
                                contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp)
                            ) {
                                items(
                                    items = jobs,
                                    key = { it.id }
                                ) { job ->
                                    JobCard(
                                        job = job,
                                        appLanguage = appLanguage,
                                        onClick = {
                                            viewModel.selectJob(job)
                                            onJobClick(job)
                                        },
                                        onApplyClick = {
                                            viewModel.selectJob(job)
                                            onJobClick(job)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyFeedState(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFF1F5F9),
            modifier = Modifier.size(72.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.WorkOutline,
                    contentDescription = null,
                    tint = NeutralMedium,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = NeutralDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = NeutralMedium,
            textAlign = TextAlign.Center
        )
    }
}
