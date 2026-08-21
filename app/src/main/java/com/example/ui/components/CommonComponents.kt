package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandAmberLight
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.BrandGreenSurface
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium
import com.example.ui.viewmodel.FeedTab

/**
 * Header ya Juu ya ClipZone yenye nembo, jina, na kiashiria cha Offline
 */
@Composable
fun AppTopHeader(
    isOffline: Boolean,
    onRoleClick: () -> Unit = {},
    userRoleText: String = "Mtafuta Kazi"
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
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Logo Icon Box
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(BrandGreen, BrandGreenDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "ClipZone Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ClipZone",
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreenDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AJIRA",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = BrandAmber
                            )
                        }
                        Text(
                            text = "Ungana na Fursa za Tanzania",
                            fontSize = 11.sp,
                            color = NeutralMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Role badge or Profile Chip
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BrandGreenLight,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onRoleClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Role",
                            tint = BrandGreenDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = userRoleText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandGreenDark
                        )
                    }
                }
            }

            // Offline status warning banner if internet is disconnected
            if (isOffline) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF3CD))
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = "Offline Mode",
                        tint = Color(0xFF856404),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hali ya Nje ya Mtandao (Offline Cache inatumika)",
                        fontSize = 12.sp,
                        color = Color(0xFF856404),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * TabButton 2 Kubwa Juu: "WATAFUTA KAZI" na "WAJIRI"
 */
@Composable
fun FeedTabSelector(
    selectedTab: FeedTab,
    jobSeekersCount: Int,
    employersCount: Int,
    onTabSelected: (FeedTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("feed_tab_selector"),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE2E8F0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 1: WATAFUTA KAZI
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
                    .clip(RoundedCornerShape(12.dp))
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
                        contentDescription = "Watafuta Kazi",
                        tint = tab1TextColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WATAFUTA KAZI",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = tab1TextColor
                    )
                    if (jobSeekersCount > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (isTab1Selected) Color.White.copy(alpha = 0.25f) else Color(0xFFCBD5E1)
                        ) {
                            Text(
                                text = "$jobSeekersCount",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = tab1TextColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            // Tab 2: WAJIRI (Kazi zote zilizopo)
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
                    .clip(RoundedCornerShape(12.dp))
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
                        contentDescription = "Wajiri",
                        tint = tab2TextColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WAJIRI",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = tab2TextColor
                    )
                    if (employersCount > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (isTab2Selected) Color.White.copy(alpha = 0.25f) else Color(0xFFCBD5E1)
                        ) {
                            Text(
                                text = "$employersCount",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = tab2TextColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * SearchBar ya kutafuta kwa Cheo au Jina
 */
@Composable
fun AppSearchBar(
    searchQuery: String,
    onSearchChanged: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("search_bar_input"),
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 14.sp,
                color = NeutralMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Tafuta",
                tint = BrandGreen
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrandGreen,
            unfocusedBorderColor = Color(0xFFCBD5E1),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

/**
 * Filter Chips kwa ajili ya Mikoa na Kada
 */
@Composable
fun LocationAndCategoryFilterBar(
    selectedLocation: String,
    onLocationSelected: (String) -> Unit,
    locations: List<String> = listOf("Mikoa Yote", "Dar es Salaam", "Arusha", "Dodoma", "Mwanza", "Mbeya", "Morogoro", "Tanga")
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
    ) {
        items(locations) { loc ->
            val isSelected = selectedLocation == loc
            FilterChip(
                selected = isSelected,
                onClick = { onLocationSelected(loc) },
                label = {
                    Text(
                        text = loc,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
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
 * Kadi ya Mtafuta Kazi: Jina, Picha, Ujuzi, Mkoa, Button "Wasiliana"
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobSeekerCard(
    profile: JobSeekerProfile,
    onClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
            .testTag("job_seeker_card_${profile.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Box yenye initial au picha
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(BrandGreenLight, Color(0xFFC8E6C9))
                            )
                        )
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
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandGreenDark
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.fullName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = profile.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandGreen,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Eneo",
                            tint = NeutralMedium,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = profile.location,
                            fontSize = 12.sp,
                            color = NeutralMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Ujuzi / Skills Chips
            if (profile.skills.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3
                ) {
                    profile.skills.take(4).forEach { skill ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Text(
                                text = "• $skill",
                                fontSize = 11.sp,
                                color = Color(0xFF334155),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Uzoefu & Mshahara
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = BrandBlueLight
                ) {
                    Text(
                        text = profile.availability,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (profile.salaryExpectation.isNotBlank()) {
                    Text(
                        text = profile.salaryExpectation,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandAmber
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Button "Wasiliana" & "Angalia Wasifu"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
                ) {
                    Text(text = "Angalia Wasifu", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onContactClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Piga Simu",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Wasiliana", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * Kadi ya Ajira: Cheo, Kampuni, Mkoa, Mshahara, Button "Tuma Maombi"
 */
@Composable
fun JobCard(
    job: Job,
    onClick: () -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
            .testTag("job_card_${job.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cheo na Badge ya Aina ya Kazi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.jobTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = "Kampuni",
                            tint = BrandGreen,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = job.company,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandGreenDark
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = BrandGreenLight
                ) {
                    Text(
                        text = job.jobType,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandGreenDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Mkoa na Mshahara
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Mkoa",
                        tint = NeutralMedium,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = job.location,
                        fontSize = 12.sp,
                        color = NeutralMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = "Mshahara",
                        tint = BrandAmber,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = job.salary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandAmber
                    )
                }
            }

            if (job.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = job.description,
                    fontSize = 12.sp,
                    color = Color(0xFF475569),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions: Maelezo Zaidi & Tuma Maombi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
                ) {
                    Text(text = "Maelezo Kamili", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onApplyClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Tuma Maombi",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Tuma Maombi", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
