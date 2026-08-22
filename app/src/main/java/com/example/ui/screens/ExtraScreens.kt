package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AppNotification
import com.example.data.model.UserRole
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium
import com.example.ui.viewmodel.AjiraViewModel
import com.example.util.AppLanguage
import com.example.util.AppStrings

/**
 * Screen ya Arifa (Notifications) yenye Lugha 2
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: AjiraViewModel
) {
    val appLanguage by viewModel.appLanguage.collectAsState()
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AppStrings.notifications(appLanguage),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandGreen
                )
            )
        }
    ) { paddingValues ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = AppStrings.noNotifications(appLanguage),
                    color = NeutralMedium,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(notifications, key = { it.id }) { notif ->
                    NotificationItemCard(notif)
                }
            }
        }
    }
}

@Composable
fun NotificationItemCard(notification: AppNotification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (notification.type == "job") BrandGreenLight else BrandBlueLight
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (notification.type == "job") Icons.Default.Work else Icons.Default.Person,
                    contentDescription = null,
                    tint = if (notification.type == "job") BrandGreenDark else BrandBlue,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = notification.timeAgo,
                        fontSize = 11.sp,
                        color = NeutralMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    fontSize = 13.sp,
                    color = Color(0xFF475569),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

/**
 * Screen ya Wasifu Wangu & Mipangilio (My Profile with Image Upload & Language Switcher)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    viewModel: AjiraViewModel,
    onLoginClick: () -> Unit = {}
) {
    val appLanguage by viewModel.appLanguage.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val isOffline by viewModel.isOfflineMode.collectAsState()
    val context = LocalContext.current

    // Local form state
    var fullName by remember(currentUser.fullName) { mutableStateOf(currentUser.fullName) }
    var phoneNumber by remember(currentUser.phoneNumber) { mutableStateOf(currentUser.phoneNumber) }
    var email by remember(currentUser.email) { mutableStateOf(currentUser.email) }
    var location by remember(currentUser.location) { mutableStateOf(currentUser.location) }
    var profession by remember(currentUser.profession) { mutableStateOf(currentUser.profession) }
    var bio by remember(currentUser.bio) { mutableStateOf(currentUser.bio) }
    var avatarUrl by remember(currentUser.avatarUrl) { mutableStateOf(currentUser.avatarUrl) }
    var selectedRole by remember(currentUser.role) { mutableStateOf(currentUser.role) }

    // Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            avatarUrl = it.toString()
        }
    }

    // Privacy & Terms dialog
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AppStrings.profile(appLanguage),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandGreen
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Profile Card with Avatar & Image Upload
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Image with Camera overlay icon
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.size(90.dp)
                    ) {
                        if (avatarUrl.isNotBlank()) {
                            AsyncImage(
                                model = avatarUrl,
                                contentDescription = "Profile Photo",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .border(2.5.dp, BrandGreen, CircleShape)
                                    .clickable { imagePickerLauncher.launch("image/*") },
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(listOf(BrandGreenLight, Color(0xFFC8E6C9)))
                                    )
                                    .border(2.5.dp, BrandGreen, CircleShape)
                                    .clickable { imagePickerLauncher.launch("image/*") },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = BrandGreenDark,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }

                        // Upload Camera Floating Badge
                        Surface(
                            shape = CircleShape,
                            color = BrandGreenDark,
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .clickable { imagePickerLauncher.launch("image/*") }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = "Upload Photo",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Buttons to Choose or Remove Image
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = BrandGreenDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (avatarUrl.isBlank()) AppStrings.uploadPhoto(appLanguage) else AppStrings.changePhoto(appLanguage),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreenDark
                            )
                        }

                        if (avatarUrl.isNotBlank()) {
                            TextButton(
                                onClick = { avatarUrl = "" },
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = AppStrings.removePhoto(appLanguage),
                                    fontSize = 11.sp,
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = fullName.ifBlank { "Mtumiaji wa ClipZone" },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )

                    if (profession.isNotBlank()) {
                        Text(
                            text = profession,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandGreenDark,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    if (location.isNotBlank()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = NeutralMedium,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = location,
                                fontSize = 12.sp,
                                color = NeutralMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BrandGreenLight
                    ) {
                        Text(
                            text = "${AppStrings.role(appLanguage)}: ${selectedRole.getDisplayName(appLanguage)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandGreenDark,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Language Selection Card (Lugha: Swahili / English)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = BrandGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = AppStrings.language(appLanguage),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeutralDark
                                )
                                Text(
                                    text = if (appLanguage == AppLanguage.SWAHILI) "Kiswahili (Tanzania)" else "English (Global)",
                                    fontSize = 12.sp,
                                    color = NeutralMedium
                                )
                            }
                        }

                        // Toggle Buttons for Language
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (appLanguage == AppLanguage.SWAHILI) BrandGreen else Color(0xFFE2E8F0),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { viewModel.setAppLanguage(AppLanguage.SWAHILI) }
                            ) {
                                Text(
                                    text = "🇹🇿 Swahili",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appLanguage == AppLanguage.SWAHILI) Color.White else NeutralDark,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (appLanguage == AppLanguage.ENGLISH) BrandGreen else Color(0xFFE2E8F0),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { viewModel.setAppLanguage(AppLanguage.ENGLISH) }
                            ) {
                                Text(
                                    text = "🇬🇧 English",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (appLanguage == AppLanguage.ENGLISH) Color.White else NeutralDark,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Switch Role Card ("Natafuta Kazi" vs "Ninaajiri")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null,
                                tint = BrandGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = AppStrings.role(appLanguage),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeutralDark
                                )
                                Text(
                                    text = selectedRole.getDisplayName(appLanguage),
                                    fontSize = 12.sp,
                                    color = NeutralMedium
                                )
                            }
                        }

                        Switch(
                            checked = selectedRole == UserRole.EMPLOYER,
                            onCheckedChange = { isEmployer ->
                                val newRole = if (isEmployer) UserRole.EMPLOYER else UserRole.JOB_SEEKER
                                selectedRole = newRole
                                viewModel.switchUserRole(newRole)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = BrandGreen,
                                checkedTrackColor = BrandGreenLight
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // FOMU YA KUJAZA / KUBADILI TAARIFA (DIRECT PROFILE EDITOR)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = BrandGreenDark,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (appLanguage == AppLanguage.SWAHILI) "Taarifa Zangu za Wasifu" else "My Profile Information",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeutralDark
                        )
                    }

                    Text(
                        text = if (appLanguage == AppLanguage.SWAHILI) "Weka taarifa zako hapa ili zitumike moja kwa moja unapoweka kazi au kutafuta ajira." else "Enter your information here to auto-fill your job or profile posts.",
                        fontSize = 12.sp,
                        color = NeutralMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                    )

                    // Jina Kamili
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(AppStrings.fullName(appLanguage)) },
                        placeholder = { Text(if (appLanguage == AppLanguage.SWAHILI) "Mfano: Abdul Bashiru" else "e.g. John Doe") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BrandGreen) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_name"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Nambari ya Simu
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text(AppStrings.phoneNumber(appLanguage)) },
                        placeholder = { Text("+255 7XX XXX XXX") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BrandGreen) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_phone"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Barua Pepe (Email)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(AppStrings.email(appLanguage)) },
                        placeholder = { Text("example@gmail.com") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BrandGreen) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_email"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Mkoa / Eneo
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text(AppStrings.location(appLanguage)) },
                        placeholder = { Text(if (appLanguage == AppLanguage.SWAHILI) "Mfano: Dar es Salaam, Kinondoni" else "e.g. Dar es Salaam") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = BrandGreen) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_location"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    // Orodha ya Mikoa ya Tanzania
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(AppStrings.TANZANIA_REGIONS) { reg ->
                            val isSelected = location.contains(reg, ignoreCase = true)
                            FilterChip(
                                selected = isSelected,
                                onClick = { location = reg },
                                label = { Text(reg, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BrandGreenLight,
                                    selectedLabelColor = BrandGreenDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Taaluma au Biashara
                    OutlinedTextField(
                        value = profession,
                        onValueChange = { profession = it },
                        label = { Text(AppStrings.jobTitle(appLanguage)) },
                        placeholder = { Text(if (appLanguage == AppLanguage.SWAHILI) "Mfano: Dereva wa Malori, Mhasibu, Fundi Umeme" else "e.g. Driver, Accountant, Electrician") },
                        leadingIcon = { Icon(Icons.Default.Work, contentDescription = null, tint = BrandGreen) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_profession"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Maelezo Mafupi (Bio)
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text(AppStrings.bio(appLanguage)) },
                        placeholder = { Text(if (appLanguage == AppLanguage.SWAHILI) "Eleza uzoefu au huduma unazotoa..." else "Describe your experience or services...") },
                        leadingIcon = { Icon(Icons.Default.Description, contentDescription = null, tint = BrandGreen) },
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_profile_bio"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            viewModel.updateUserProfile(
                                fullName = fullName.trim(),
                                phoneNumber = phoneNumber.trim(),
                                email = email.trim(),
                                location = location.trim(),
                                profession = profession.trim(),
                                bio = bio.trim(),
                                avatarUrl = avatarUrl.trim(),
                                role = selectedRole
                            )
                            Toast.makeText(
                                context,
                                if (appLanguage == AppLanguage.SWAHILI) "Taarifa zako zimehifadhiwa kikamilifu!" else "Profile saved successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_save_profile_info"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandGreenDark,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = AppStrings.save(appLanguage), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Taarifa za App & Play Store Readiness
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (appLanguage == AppLanguage.SWAHILI) "Kuhusu ClipZone Ajira" else "About ClipZone Ajira",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = if (appLanguage == AppLanguage.SWAHILI) "Toleo la App" else "App Version", fontSize = 13.sp, color = NeutralMedium)
                        Text(text = "v1.0.0 (Play Store Ready)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BrandGreenDark)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Package Name", fontSize = 13.sp, color = NeutralMedium)
                        Text(text = "com.clipzone.ajira", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = NeutralDark)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = if (appLanguage == AppLanguage.SWAHILI) "Hali ya Mtandao" else "Network Status", fontSize = 13.sp, color = NeutralMedium)
                        Text(
                            text = if (isOffline) {
                                if (appLanguage == AppLanguage.SWAHILI) "Nje ya Mtandao (Offline Cache)" else "Offline (Local Cache)"
                            } else {
                                if (appLanguage == AppLanguage.SWAHILI) "Moja kwa Moja (Live Firestore)" else "Online (Live Firestore)"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isOffline) BrandAmber else BrandGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sera ya Faragha na Vigezo (Play Store Compliance)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { showPrivacyDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Policy, contentDescription = null, modifier = Modifier.size(16.dp), tint = BrandGreen)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = AppStrings.privacyPolicy(appLanguage), fontSize = 12.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { showTermsDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp), tint = NeutralDark)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = AppStrings.termsOfService(appLanguage), fontSize = 12.sp, color = NeutralDark, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Dialog ya Sera ya Faragha (Privacy Policy)
    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = {
                Text(
                    text = AppStrings.privacyPolicy(appLanguage),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = BrandGreenDark
                )
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = if (appLanguage == AppLanguage.SWAHILI) {
                            "ClipZone Ajira inaheshimu faragha ya watumiaji wote:\n\n" +
                                    "1. Taarifa kama Jina, Namba ya Simu, Picha na Mahali unapoishi zinatumika kuwawezesha waajiri na watafuta kazi kuwasiliana moja kwa moja.\n\n" +
                                    "2. Hatushiriki taarifa zako za siri na wahusika wengine wasiohusika na shughuli za ajira.\n\n" +
                                    "3. Hakuna ulazima wa kutumia nywila (passwords) au kadi za benki; huduma zote za msingi ni rahisi na wazi kwa kila mtu."
                        } else {
                            "ClipZone Ajira respects the privacy of all users:\n\n" +
                                    "1. Information such as Name, Phone Number, Photo, and Location are used solely to connect job seekers and employers directly.\n\n" +
                                    "2. We do not sell or share your private information with third parties.\n\n" +
                                    "3. No passwords or credit cards are required; basic services are open and accessible to all."
                        },
                        fontSize = 13.sp,
                        color = NeutralDark,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text(if (appLanguage == AppLanguage.SWAHILI) "Nimeelewa" else "Understood", fontWeight = FontWeight.Bold, color = BrandGreen)
                }
            }
        )
    }

    // Dialog ya Vigezo na Masharti (Terms)
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = {
                Text(
                    text = AppStrings.termsOfService(appLanguage),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = BrandGreenDark
                )
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = if (appLanguage == AppLanguage.SWAHILI) {
                            "Vigezo vya matumizi ya ClipZone Ajira:\n\n" +
                                    "1. Matangazo yote ya ajira na wasifu lazima yawe ya kweli na ya halali nchini Tanzania.\n\n" +
                                    "2. Ni marufuku kuweka matangazo ya ulaghai au yanayodai fedha kabla ya usaili.\n\n" +
                                    "3. ClipZone Ajira inalenga kuwa daraja la haraka kati ya watoa ajira na watafuta fursa."
                        } else {
                            "Terms of use for ClipZone Ajira:\n\n" +
                                    "1. All job listings and profiles must be authentic and lawful.\n\n" +
                                    "2. Fraudulent listings or requesting payment prior to interviews is strictly prohibited.\n\n" +
                                    "3. ClipZone Ajira serves as a direct bridge connecting opportunities with talent."
                        },
                        fontSize = 13.sp,
                        color = NeutralDark,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text(if (appLanguage == AppLanguage.SWAHILI) "Sawa" else "OK", fontWeight = FontWeight.Bold, color = BrandGreen)
                }
            }
        )
    }
}
