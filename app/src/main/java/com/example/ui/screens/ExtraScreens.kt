package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

/**
 * Screen ya Arifa (Notifications)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    viewModel: AjiraViewModel
) {
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Arifa & Taarifa",
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
                    text = "Hakuna arifa mpya kwa sasa.",
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
 * Screen ya Wasifu Wangu & Mipangilio (My Profile & Direct User Details)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    viewModel: AjiraViewModel,
    onLoginClick: () -> Unit = {}
) {
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
    var selectedRole by remember(currentUser.role) { mutableStateOf(currentUser.role) }

    // Privacy & Terms dialog
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wasifu na Taarifa Zangu",
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
            // Header Profile Card
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
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(BrandGreenLight, Color(0xFFC8E6C9)))
                            )
                            .border(2.dp, BrandGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = BrandGreenDark,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

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
                            text = "Hali: ${selectedRole.displayNameSwahili}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandGreenDark,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                                    text = "Jukumu Lako Kuu",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeutralDark
                                )
                                Text(
                                    text = if (selectedRole == UserRole.JOB_SEEKER) "Natafuta Kazi / Ajira" else "Ninaajiri (Mwajiri / Kampuni)",
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

            Spacer(modifier = Modifier.height(16.dp))

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
                            text = "Taarifa Zangu za Mawasiliano",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeutralDark
                        )
                    }

                    Text(
                        text = "Weka taarifa zako hapa ili zitumike moja kwa moja unapoweka kazi au kutafuta ajira.",
                        fontSize = 12.sp,
                        color = NeutralMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                    )

                    // Jina Kamili
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Jina Lako Kamili") },
                        placeholder = { Text("Mfano: Abdul Bashiru") },
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
                        label = { Text("Nambari ya Simu ya Kupigiwa / WhatsApp") },
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
                        label = { Text("Barua Pepe (Email) - Sio Lazima") },
                        placeholder = { Text("mfano@gmail.com") },
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
                        label = { Text("Mkoa / Eneo Unapoishi") },
                        placeholder = { Text("Mfano: Dar es Salaam, Kinondoni") },
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Taaluma au Biashara
                    OutlinedTextField(
                        value = profession,
                        onValueChange = { profession = it },
                        label = { Text("Taaluma Yako au Jina la Biashara") },
                        placeholder = { Text("Mfano: Dereva wa Malori, Mhasibu, Fundi Umeme") },
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
                        label = { Text("Kuhusu Wewe / Maelezo ya Ziada") },
                        placeholder = { Text("Eleza uzoefu au huduma unazotoa...") },
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
                                role = selectedRole
                            )
                            Toast.makeText(context, "Taarifa zako zimehifadhiwa kikamilifu!", Toast.LENGTH_SHORT).show()
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
                        Text(text = "Hifadhi Taarifa Zangu", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Taarifa za App & Play Store Readiness
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Kuhusu ClipZone Ajira",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Toleo la App", fontSize = 13.sp, color = NeutralMedium)
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
                        Text(text = "Hali ya Mtandao", fontSize = 13.sp, color = NeutralMedium)
                        Text(
                            text = if (isOffline) "Nje ya Mtandao (Offline Cache)" else "Moja kwa Moja (Live Firestore)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isOffline) BrandAmber else BrandGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                    Text(text = "Sera ya Faragha", fontSize = 12.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { showTermsDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp), tint = NeutralDark)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Vigezo & Masharti", fontSize = 12.sp, color = NeutralDark, fontWeight = FontWeight.Bold)
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
                    text = "Sera ya Faragha (Privacy Policy)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = BrandGreenDark
                )
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "ClipZone Ajira inaheshimu faragha ya watumiaji wote:\n\n" +
                                "1. Taarifa kama Jina, Namba ya Simu, na Mahali unapoishi zinatumika kuwawezesha waajiri na watafuta kazi kuwasiliana moja kwa moja.\n\n" +
                                "2. Hatushiriki taarifa zako za siri na wahusika wengine wasiohusika na shughuli za ajira.\n\n" +
                                "3. Hakuna ulazima wa kutumia nywila (passwords) au kadi za benki; huduma zote za msingi ni rahisi na wazi kwa kila Mtanzania.",
                        fontSize = 13.sp,
                        color = NeutralDark,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Nimeelewa", fontWeight = FontWeight.Bold, color = BrandGreen)
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
                    text = "Vigezo na Masharti",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = BrandGreenDark
                )
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "Vigezo vya matumizi ya ClipZone Ajira:\n\n" +
                                "1. Matangazo yote ya ajira na wasifu lazima yawe ya kweli na ya halali nchini Tanzania.\n\n" +
                                "2. Ni marufuku kuweka matangazo ya ulaghai au yanayodai fedha kabla ya usaili.\n\n" +
                                "3. ClipZone Ajira inalenga kuwa daraja la haraka kati ya watoa ajira na watafuta fursa.",
                        fontSize = 13.sp,
                        color = NeutralDark,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Sawa", fontWeight = FontWeight.Bold, color = BrandGreen)
                }
            }
        )
    }
}
