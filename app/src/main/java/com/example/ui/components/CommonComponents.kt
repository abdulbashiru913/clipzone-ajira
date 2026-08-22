package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium
import com.example.ui.viewmodel.FeedTab
import com.example.util.AppLanguage
import com.example.util.AppStrings

/**
 * Header ya Juu ya ClipZone yenye Nembo, Lugha Switcher (SW/EN), na Role
 */
@Composable
fun AppTopHeader(
    isOffline: Boolean,
    appLanguage: AppLanguage = AppLanguage.SWAHILI,
    onLanguageToggle: () -> Unit = {},
    userRoleText: String = "Mtafuta Kazi",
    onRoleClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("app_top_header"),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo & Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.linearGradient(listOf(BrandGreen, BrandGreenDark))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "ClipZone Logo",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ClipZone",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreenDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AJIRA",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = BrandAmber
                            )
                        }
                        Text(
                            text = AppStrings.appTagline(appLanguage),
                            fontSize = 10.sp,
                            color = NeutralMedium,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Language Switcher & Role Badges
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Language Switcher Button (e.g. 🇹🇿 SW / 🇬🇧 EN)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onLanguageToggle() }
                            .testTag("language_toggle_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (appLanguage == AppLanguage.SWAHILI) "🇹🇿 SW" else "🇬🇧 EN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeutralDark
                            )
                        }
                    }

                    // Role Badge
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = BrandGreenLight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onRoleClick() }
                            .testTag("user_role_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = BrandGreenDark,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = userRoleText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreenDark
                            )
                        }
                    }
                }
            }

            // Offline status warning banner
            if (isOffline) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF3CD))
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = "Offline Mode",
                        tint = Color(0xFF856404),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (appLanguage == AppLanguage.SWAHILI) "Hali ya Nje ya Mtandao (Offline Cache)" else "Offline Mode (Local Cache Active)",
                        fontSize = 11.sp,
                        color = Color(0xFF856404),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * TabButton 2 Kubwa: "WATAFUTA KAZI" na "WAJIRI" (Bilingual)
 */
@Composable
fun FeedTabSelector(
    selectedTab: FeedTab,
    jobSeekersCount: Int,
    employersCount: Int,
    appLanguage: AppLanguage = AppLanguage.SWAHILI,
    onTabSelected: (FeedTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("feed_tab_selector"),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFE2E8F0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 1: WATAFUTA KAZI / JOB SEEKERS
            val isTab1Selected = selectedTab == FeedTab.JOB_SEEKERS
            val tab1Bg by animateColorAsState(
                targetValue = if (isTab1Selected) BrandGreen else Color.Transparent,
                label = "tab1_bg"
            )
            val tab1TextColor by animateColorAsState(
                targetValue = if (isTab1Selected) Color.White else NeutralDark,
                label = "tab1_text"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(11.dp))
                    .background(tab1Bg)
                    .clickable { onTabSelected(FeedTab.JOB_SEEKERS) }
                    .testTag("tab_job_seekers"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = tab1TextColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = AppStrings.tabJobSeekers(appLanguage),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = tab1TextColor
                    )
                    if (jobSeekersCount > 0) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (isTab1Selected) Color.White.copy(alpha = 0.25f) else Color(0xFFCBD5E1)
                        ) {
                            Text(
                                text = "$jobSeekersCount",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = tab1TextColor,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }

            // Tab 2: WAJIRI / EMPLOYERS
            val isTab2Selected = selectedTab == FeedTab.EMPLOYERS
            val tab2Bg by animateColorAsState(
                targetValue = if (isTab2Selected) BrandGreen else Color.Transparent,
                label = "tab2_bg"
            )
            val tab2TextColor by animateColorAsState(
                targetValue = if (isTab2Selected) Color.White else NeutralDark,
                label = "tab2_text"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(11.dp))
                    .background(tab2Bg)
                    .clickable { onTabSelected(FeedTab.EMPLOYERS) }
                    .testTag("tab_employers"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = tab2TextColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = AppStrings.tabEmployers(appLanguage),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = tab2TextColor
                    )
                    if (employersCount > 0) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (isTab2Selected) Color.White.copy(alpha = 0.25f) else Color(0xFFCBD5E1)
                        ) {
                            Text(
                                text = "$employersCount",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = tab2TextColor,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Filter Chips ya Mikoa (Minimal & Quick)
 */
@Composable
fun LocationAndCategoryFilterBar(
    selectedLocation: String,
    appLanguage: AppLanguage = AppLanguage.SWAHILI,
    onLocationSelected: (String) -> Unit
) {
    val allLabel = AppStrings.allLocations(appLanguage)
    val locations = listOf(allLabel) + AppStrings.TANZANIA_REGIONS

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
    ) {
        items(locations) { loc ->
            val isSelected = (selectedLocation == "Mikoa Yote" && loc == allLabel) ||
                    (selectedLocation == "All Regions" && loc == allLabel) ||
                    (selectedLocation == loc)

            FilterChip(
                selected = isSelected,
                onClick = { onLocationSelected(if (loc == allLabel) "Mikoa Yote" else loc) },
                label = {
                    Text(
                        text = loc,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = BrandGreenDark
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = BrandGreenLight,
                    selectedLabelColor = BrandGreenDark
                )
            )
        }
    }
}

/**
 * Kadi Safi na Rahisi ya Mtafuta Kazi (Minimal & Essential Only)
 * Inajumuisha Picha ya Wasifu (Image/Avatar), Jina, Ujuzi/Kazi, Eneo na Kitufe cha Kuwasiliana
 */
@Composable
fun JobSeekerCard(
    profile: JobSeekerProfile,
    appLanguage: AppLanguage = AppLanguage.SWAHILI,
    onClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onClick() }
            .testTag("job_seeker_card_${profile.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar / Profile Photo
            if (profile.avatarUrl.isNotBlank()) {
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = profile.fullName,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, BrandGreen, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(BrandGreenLight, Color(0xFFC8E6C9))))
                        .border(1.5.dp, BrandGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    val initials = profile.fullName.split(" ")
                        .take(2)
                        .mapNotNull { it.firstOrNull()?.toString() }
                        .joinToString("")
                        .ifBlank { "MK" }

                    Text(
                        text = initials,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandGreenDark
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Details: Name, Title, Location & Status
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.fullName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeutralDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = profile.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandGreenDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = NeutralMedium,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = profile.location,
                        fontSize = 11.sp,
                        color = NeutralMedium,
                        maxLines = 1
                    )
                    if (profile.availability.isNotBlank()) {
                        Text(
                            text = " • ${profile.availability}",
                            fontSize = 11.sp,
                            color = BrandBlue,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Minimal Contact Button
            Button(
                onClick = onContactClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = AppStrings.contact(appLanguage),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Kadi Safi na Rahisi ya Kazi (Minimal & Essential Only)
 * Inajumuisha Cheo, Kampuni/Mwajiri, Eneo, Malipo, na Kitufe cha Kuomba
 */
@Composable
fun JobCard(
    job: Job,
    appLanguage: AppLanguage = AppLanguage.SWAHILI,
    onClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onClick() }
            .testTag("job_card_${job.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Job Brief Icon / Details
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandGreenLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = null,
                    tint = BrandGreenDark,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.jobTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeutralDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = job.company,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandGreenDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = NeutralMedium,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = job.location,
                        fontSize = 11.sp,
                        color = NeutralMedium
                    )
                    if (job.salary.isNotBlank()) {
                        Text(
                            text = " • ${job.salary}",
                            fontSize = 11.sp,
                            color = BrandAmber,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Minimal Apply / Details Button
            Button(
                onClick = onApplyClick,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(13.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = AppStrings.applyNow(appLanguage),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
